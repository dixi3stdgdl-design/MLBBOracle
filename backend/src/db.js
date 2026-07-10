const Database = require('better-sqlite3');
const path = require('path');
const fs = require('fs');

const dbPath = process.env.DATABASE_PATH || './data/oracle.db';
const dbDir = path.dirname(dbPath);

if (!fs.existsSync(dbDir)) {
  fs.mkdirSync(dbDir, { recursive: true });
}

const db = new Database(dbPath);

db.pragma('journal_mode = WAL');
db.pragma('foreign_keys = ON');

db.exec(`
  CREATE TABLE IF NOT EXISTS users (
    id TEXT PRIMARY KEY,
    device_id TEXT UNIQUE NOT NULL,
    username TEXT NOT NULL,
    total_matches INTEGER DEFAULT 0,
    total_wins INTEGER DEFAULT 0,
    favorite_hero TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  );

  CREATE TABLE IF NOT EXISTS matches (
    id TEXT PRIMARY KEY,
    user_id TEXT NOT NULL,
    hero_name TEXT NOT NULL,
    result TEXT CHECK(result IN ('win', 'loss')) NOT NULL,
    game_mode TEXT NOT NULL,
    duration INTEGER NOT NULL,
    gold INTEGER DEFAULT 0,
    kills INTEGER DEFAULT 0,
    deaths INTEGER DEFAULT 0,
    assists INTEGER DEFAULT 0,
    damage INTEGER DEFAULT 0,
    cs INTEGER DEFAULT 0,
    items_json TEXT DEFAULT '[]',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
  );

  CREATE TABLE IF NOT EXISTS heroes_stats (
    id TEXT PRIMARY KEY,
    hero_name TEXT NOT NULL,
    user_id TEXT NOT NULL,
    matches_played INTEGER DEFAULT 0,
    wins INTEGER DEFAULT 0,
    avg_kda REAL DEFAULT 0,
    avg_gold REAL DEFAULT 0,
    avg_damage REAL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE(hero_name, user_id)
  );

  CREATE INDEX IF NOT EXISTS idx_matches_user ON matches(user_id);
  CREATE INDEX IF NOT EXISTS idx_matches_hero ON matches(hero_name);
  CREATE INDEX IF NOT EXISTS idx_heroes_stats_user ON heroes_stats(user_id);
`);

module.exports = db;
