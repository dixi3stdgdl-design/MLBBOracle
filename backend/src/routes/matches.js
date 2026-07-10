const { v4: uuidv4 } = require('uuid');
const db = require('../db');

async function matchesRoutes(fastify, options) {
  fastify.post('/matches', async (request, reply) => {
    const {
      user_id, hero_name, result, game_mode, duration,
      gold, kills, deaths, assists, damage, cs, items
    } = request.body;

    if (!user_id || !hero_name || !result || !game_mode || !duration) {
      return reply.code(400).send({ error: 'Missing required fields' });
    }

    const id = uuidv4();
    const items_json = JSON.stringify(items || []);

    db.prepare(`
      INSERT INTO matches (id, user_id, hero_name, result, game_mode, duration, gold, kills, deaths, assists, damage, cs, items_json)
      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    `).run(id, user_id, hero_name, result, game_mode, duration, gold || 0, kills || 0, deaths || 0, assists || 0, damage || 0, cs || 0, items_json);

    const user = db.prepare('SELECT * FROM users WHERE id = ?').get(user_id);
    if (user) {
      const isWin = result === 'win';
      db.prepare(`
        UPDATE users SET
          total_matches = total_matches + 1,
          total_wins = total_wins + ?
        WHERE id = ?
      `).run(isWin ? 1 : 0, user_id);
    }

    const stats = db.prepare('SELECT * FROM heroes_stats WHERE hero_name = ? AND user_id = ?').get(hero_name, user_id);
    if (stats) {
      const newMatches = stats.matches_played + 1;
      const newWins = stats.wins + (result === 'win' ? 1 : 0);
      const kda = deaths > 0 ? (kills + assists) / deaths : kills + assists;
      const newAvgKda = (stats.avg_kda * stats.matches_played + kda) / newMatches;
      const newAvgGold = (stats.avg_gold * stats.matches_played + gold) / newMatches;
      const newAvgDamage = (stats.avg_damage * stats.matches_played + damage) / newMatches;

      db.prepare(`
        UPDATE heroes_stats SET
          matches_played = ?,
          wins = ?,
          avg_kda = ?,
          avg_gold = ?,
          avg_damage = ?
        WHERE hero_name = ? AND user_id = ?
      `).run(newMatches, newWins, newAvgKda, newAvgGold, newAvgDamage, hero_name, user_id);
    } else {
      db.prepare(`
        INSERT INTO heroes_stats (id, hero_name, user_id, matches_played, wins, avg_kda, avg_gold, avg_damage)
        VALUES (?, ?, ?, 1, ?, ?, ?, ?)
      `).run(uuidv4(), hero_name, user_id, result === 'win' ? 1 : 0, gold || 0, damage || 0);
    }

    const match = db.prepare('SELECT * FROM matches WHERE id = ?').get(id);
    return reply.code(201).send(match);
  });

  fastify.get('/matches', async (request, reply) => {
    const { user_id, hero_name, limit = 50, offset = 0 } = request.query;

    let query = 'SELECT * FROM matches WHERE 1=1';
    const params = [];

    if (user_id) {
      query += ' AND user_id = ?';
      params.push(user_id);
    }
    if (hero_name) {
      query += ' AND hero_name = ?';
      params.push(hero_name);
    }

    query += ' ORDER BY created_at DESC LIMIT ? OFFSET ?';
    params.push(Number(limit), Number(offset));

    const matches = db.prepare(query).all(...params);
    return matches;
  });

  fastify.get('/matches/:id', async (request, reply) => {
    const { id } = request.params;
    const match = db.prepare('SELECT * FROM matches WHERE id = ?').get(id);

    if (!match) {
      return reply.code(404).send({ error: 'Match not found' });
    }

    return match;
  });
}

module.exports = matchesRoutes;
