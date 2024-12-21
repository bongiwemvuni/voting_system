-- SQLite database schema

CREATE TABLE IF NOT EXISTS voters (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    has_voted INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS parties (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    symbol TEXT NOT NULL,
    vote_count INTEGER DEFAULT 0
);

-- Insert sample parties
INSERT OR IGNORE INTO parties (name, symbol) VALUES
('Purple Party', 'purple.png'),
('Orange Party', 'orange.png'),
('Green Party', 'green.png'),
('Independent Party', 'independent.png');
