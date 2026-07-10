const db = require('../db');

async function analyticsRoutes(fastify, options) {
  fastify.get('/analytics/hero-tier', async (request, reply) => {
    const heroTiers = db.prepare(`
      SELECT
        hero_name,
        COUNT(*) as total_matches,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) as wins,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate,
        ROUND(AVG(kills + assists), 1) as avg_kda,
        ROUND(AVG(gold), 0) as avg_gold,
        ROUND(AVG(damage), 0) as avg_damage
      FROM matches
      GROUP BY hero_name
      HAVING total_matches >= 5
      ORDER BY win_rate DESC
    `).all();

    const tiered = heroTiers.map(hero => ({
      ...hero,
      tier: hero.win_rate >= 55 ? 'S' :
            hero.win_rate >= 52 ? 'A' :
            hero.win_rate >= 48 ? 'B' :
            hero.win_rate >= 45 ? 'C' : 'D'
    }));

    return tiered;
  });

  fastify.get('/analytics/win-rates', async (request, reply) => {
    const overall = db.prepare(`
      SELECT
        COUNT(*) as total,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) as wins,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate
      FROM matches
    `).get();

    const byMode = db.prepare(`
      SELECT
        game_mode,
        COUNT(*) as total,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) as wins,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate
      FROM matches
      GROUP BY game_mode
      ORDER BY total DESC
    `).all();

    const byRole = db.prepare(`
      SELECT
        hero_name,
        COUNT(*) as total,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) as wins,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate
      FROM matches
      GROUP BY hero_name
      ORDER BY total DESC
      LIMIT 20
    `).all();

    return { overall, byMode, byRole };
  });

  fastify.get('/analytics/meta-analysis', async (request, reply) => {
    const topHeroes = db.prepare(`
      SELECT
        hero_name,
        COUNT(*) as pick_rate,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate,
        ROUND(AVG(kills), 1) as avg_kills,
        ROUND(AVG(deaths), 1) as avg_deaths,
        ROUND(AVG(assists), 1) as avg_assists,
        ROUND(AVG(gold), 0) as avg_gold,
        ROUND(AVG(damage), 0) as avg_damage
      FROM matches
      GROUP BY hero_name
      ORDER BY pick_rate DESC
      LIMIT 15
    `).all();

    const recentTrends = db.prepare(`
      SELECT
        hero_name,
        COUNT(*) as matches,
        ROUND(SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) as win_rate
      FROM matches
      WHERE created_at >= datetime('now', '-7 days')
      GROUP BY hero_name
      HAVING matches >= 3
      ORDER BY win_rate DESC
      LIMIT 10
    `).all();

    const averageStats = db.prepare(`
      SELECT
        ROUND(AVG(kills), 1) as avg_kills,
        ROUND(AVG(deaths), 1) as avg_deaths,
        ROUND(AVG(assists), 1) as avg_assists,
        ROUND(AVG(gold), 0) as avg_gold,
        ROUND(AVG(damage), 0) as avg_damage,
        ROUND(AVG(duration), 0) as avg_duration
      FROM matches
    `).get();

    return { topHeroes, recentTrends, averageStats };
  });
}

module.exports = analyticsRoutes;
