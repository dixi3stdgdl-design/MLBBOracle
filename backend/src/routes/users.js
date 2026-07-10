const { v4: uuidv4 } = require('uuid');
const db = require('../db');

async function usersRoutes(fastify, options) {
  fastify.post('/users/register', async (request, reply) => {
    const { device_id, username } = request.body;

    if (!device_id || !username) {
      return reply.code(400).send({ error: 'device_id and username are required' });
    }

    let user = db.prepare('SELECT * FROM users WHERE device_id = ?').get(device_id);

    if (user) {
      if (username !== user.username) {
        db.prepare('UPDATE users SET username = ? WHERE id = ?').run(username, user.id);
        user.username = username;
      }
      return user;
    }

    const id = uuidv4();
    db.prepare(`
      INSERT INTO users (id, device_id, username)
      VALUES (?, ?, ?)
    `).run(id, device_id, username);

    user = db.prepare('SELECT * FROM users WHERE id = ?').get(id);
    return reply.code(201).send(user);
  });

  fastify.get('/users/:id/stats', async (request, reply) => {
    const { id } = request.params;
    const user = db.prepare('SELECT * FROM users WHERE id = ?').get(id);

    if (!user) {
      return reply.code(404).send({ error: 'User not found' });
    }

    const winRate = user.total_matches > 0
      ? ((user.total_wins / user.total_matches) * 100).toFixed(1)
      : 0;

    const recentMatches = db.prepare(`
      SELECT * FROM matches WHERE user_id = ? ORDER BY created_at DESC LIMIT 10
    `).all(id);

    return {
      ...user,
      win_rate: parseFloat(winRate),
      recent_matches: recentMatches
    };
  });

  fastify.get('/users/:id/hero-stats', async (request, reply) => {
    const { id } = request.params;

    const heroStats = db.prepare(`
      SELECT * FROM heroes_stats WHERE user_id = ?
      ORDER BY matches_played DESC
    `).all(id);

    return heroStats;
  });
}

module.exports = usersRoutes;
