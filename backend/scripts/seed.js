require('dotenv').config();
const { v4: uuidv4 } = require('uuid');
const db = require('../src/db');

const MLBB_HEROES = [
  // Fighters
  { name: 'Alucard', role: 'Fighter', tier: 'A' },
  { name: 'Chou', role: 'Fighter', tier: 'S' },
  { name: 'Yu Zhong', role: 'Fighter', tier: 'S' },
  { name: 'Paquito', role: 'Fighter', tier: 'A' },
  { name: 'Thamuz', role: 'Fighter', tier: 'A' },
  { name: 'Terizla', role: 'Fighter', tier: 'B' },
  { name: 'Leomord', role: 'Fighter', tier: 'B' },
  { name: 'X.Borg', role: 'Fighter', tier: 'A' },
  { name: 'Esmeralda', role: 'Fighter', tier: 'A' },
  { name: 'Lapu-Lapu', role: 'Fighter', tier: 'S' },
  { name: 'Benedetta', role: 'Fighter', tier: 'S' },
  { name: 'Dyroth', role: 'Fighter', tier: 'B' },
  { name: 'Argus', role: 'Fighter', tier: 'B' },
  { name: 'Zilong', role: 'Fighter', tier: 'C' },
  { name: 'Freya', role: 'Fighter', tier: 'B' },

  // Assassins
  { name: 'Lancelot', role: 'Assassin', tier: 'S' },
  { name: 'Fanny', role: 'Assassin', tier: 'S' },
  { name: 'Gusion', role: 'Assassin', tier: 'A' },
  { name: 'Hayabusa', role: 'Assassin', tier: 'A' },
  { name: 'Karina', role: 'Assassin', tier: 'B' },
  { name: 'Natalia', role: 'Assassin', tier: 'B' },
  { name: 'Helcurt', role: 'Assassin', tier: 'B' },
  { name: 'Ling', role: 'Assassin', tier: 'S' },
  { name: 'Benedetta', role: 'Assassin', tier: 'S' },
  { name: 'Aamon', role: 'Assassin', tier: 'A' },

  // Mages
  { name: 'Eudora', role: 'Mage', tier: 'B' },
  { name: 'Aurora', role: 'Mage', tier: 'A' },
  { name: 'Kagura', role: 'Mage', tier: 'S' },
  { name: 'Harley', role: 'Mage', tier: 'A' },
  { name: 'Valentina', role: 'Mage', tier: 'S' },
  { name: 'Lunox', role: 'Mage', tier: 'S' },
  { name: 'Yve', role: 'Mage', tier: 'A' },
  { name: 'Pharsa', role: 'Mage', tier: 'A' },
  { name: 'Cyclops', role: 'Mage', tier: 'B' },
  { name: 'Alice', role: 'Mage', tier: 'A' },
  { name: 'Zhask', role: 'Mage', tier: 'B' },
  { name: 'Change', role: 'Mage', tier: 'C' },

  // Marksmen
  { name: 'Miya', role: 'Marksman', tier: 'B' },
  { name: 'Lesley', role: 'Marksman', tier: 'A' },
  { name: 'Wanwan', role: 'Marksman', tier: 'S' },
  { name: 'Layla', role: 'Marksman', tier: 'C' },
  { name: 'Bruno', role: 'Marksman', tier: 'A' },
  { name: 'Moskov', role: 'Marksman', tier: 'B' },
  { name: 'Claude', role: 'Marksman', tier: 'S' },
  { name: 'Karrie', role: 'Marksman', tier: 'A' },
  { name: 'Beatrix', role: 'Marksman', tier: 'S' },
  { name: 'Yi Sun-shin', role: 'Marksman', tier: 'A' },

  // Tanks
  { name: 'Tigreal', role: 'Tank', tier: 'B' },
  { name: 'Akai', role: 'Tank', tier: 'A' },
  { name: 'Minotaur', role: 'Tank', tier: 'A' },
  { name: 'Johnson', role: 'Tank', tier: 'A' },
  { name: 'Hylos', role: 'Tank', tier: 'B' },
  { name: 'Grock', role: 'Tank', tier: 'A' },
  { name: 'Khufra', role: 'Tank', tier: 'S' },
  { name: 'Atlas', role: 'Tank', tier: 'S' },
  { name: 'Belerick', role: 'Tank', tier: 'B' },
  { name: 'Uranus', role: 'Tank', tier: 'A' },

  // Supports
  { name: 'Rafaela', role: 'Support', tier: 'B' },
  { name: 'Angela', role: 'Support', tier: 'S' },
  { name: 'Estes', role: 'Support', tier: 'A' },
  { name: 'Diggie', role: 'Support', tier: 'A' },
  { name: 'Floryn', role: 'Support', tier: 'B' },
  { name: 'Mathilda', role: 'Support', tier: 'A' }
];

const GAME_MODES = ['Ranked', 'Classic', 'Brawl', 'Custom'];
const RESULTS = ['win', 'loss'];

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateStats(hero) {
  const baseKills = hero.tier === 'S' ? 8 : hero.tier === 'A' ? 6 : 4;
  const baseDeaths = hero.role === 'Tank' ? 4 : hero.role === 'Assassin' ? 5 : 6;

  return {
    duration: randomInt(600, 1800),
    gold: randomInt(5000, 15000),
    kills: randomInt(baseKills - 3, baseKills + 3),
    deaths: randomInt(baseDeaths - 2, baseDeaths + 2),
    assists: randomInt(3, 15),
    damage: randomInt(15000, 80000),
    cs: randomInt(30, 120)
  };
}

console.log('🎮 Seeding MLBB Oracle database...\n');

const insertHeroStats = db.prepare(`
  INSERT OR REPLACE INTO heroes_stats (id, hero_name, user_id, matches_played, wins, avg_kda, avg_gold, avg_damage)
  VALUES (?, ?, ?, ?, ?, ?, ?, ?)
`);

const insertMatch = db.prepare(`
  INSERT INTO matches (id, user_id, hero_name, result, game_mode, duration, gold, kills, deaths, assists, damage, cs, items_json)
  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
`);

const insertUser = db.prepare(`
  INSERT OR REPLACE INTO users (id, device_id, username, total_matches, total_wins, favorite_hero)
  VALUES (?, ?, ?, ?, ?, ?)
`);

let totalMatches = 0;

const transaction = db.transaction(() => {
  // Create demo users
  const demoUsers = [
    { id: 'user-demo-001', device_id: 'android-demo-001', username: 'ProPlayer' },
    { id: 'user-demo-002', device_id: 'android-demo-002', username: 'MLBBMaster' },
    { id: 'user-demo-003', device_id: 'android-demo-003', username: 'RankedGrinder' }
  ];

  for (const user of demoUsers) {
    insertUser.run(user.id, user.device_id, user.username, 0, 0, null);
  }

  // Generate matches for each hero
  for (const hero of MLBB_HEROES) {
    const matchesCount = randomInt(10, 30);
    let heroWins = 0;

    for (let i = 0; i < matchesCount; i++) {
      const stats = generateStats(hero);
      const result = RESULTS[randomInt(0, 1)];
      if (result === 'win') heroWins++;

      const user = demoUsers[randomInt(0, 2)];
      const gameMode = GAME_MODES[randomInt(0, 2)];

      insertMatch.run(
        uuidv4(),
        user.id,
        hero.name,
        result,
        gameMode,
        stats.duration,
        stats.gold,
        stats.kills,
        stats.deaths,
        stats.assists,
        stats.damage,
        stats.cs,
        '[]'
      );
      totalMatches++;
    }

    // Calculate hero stats
    const avgKda = ((heroWins * 8 + (matchesCount - heroWins) * 4) / matchesCount).toFixed(1);
    const avgGold = randomInt(7000, 12000);
    const avgDamage = randomInt(25000, 55000);

    insertHeroStats.run(
      uuidv4(),
      hero.name,
      demoUsers[0].id,
      matchesCount,
      heroWins,
      parseFloat(avgKda),
      avgGold,
      avgDamage
    );

    console.log(`  ✓ ${hero.name} (${hero.role}) - ${matchesCount} matches, ${heroWins} wins`);
  }

  // Update user stats
  for (const user of demoUsers) {
    const stats = db.prepare(`
      SELECT
        COUNT(*) as total_matches,
        SUM(CASE WHEN result = 'win' THEN 1 ELSE 0 END) as wins
      FROM matches WHERE user_id = ?
    `).get(user.id);

    const favHero = db.prepare(`
      SELECT hero_name FROM matches WHERE user_id = ?
      GROUP BY hero_name ORDER BY COUNT(*) DESC LIMIT 1
    `).get(user.id);

    db.prepare(`
      UPDATE users SET total_matches = ?, total_wins = ?, favorite_hero = ?
      WHERE id = ?
    `).run(stats.total_matches, stats.wins, favHero?.hero_name, user.id);
  }
});

transaction();

console.log(`\n✅ Database seeded successfully!`);
console.log(`   ${MLBB_HEROES.length} heroes added`);
console.log(`   ${totalMatches} matches generated`);
console.log(`   3 demo users created\n`);
