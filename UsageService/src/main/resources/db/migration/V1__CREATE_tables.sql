CREATE TABLE energyusage(
    hour TIMESTAMP PRIMARY KEY,
    community_produced DOUBLE PRECISION NOT NULL,
    community_used DOUBLE PRECISION NOT NULL,
    grid_used DOUBLE PRECISION NOT NULL
);
CREATE TABLE currentpercentage(
    hour TIMESTAMP PRIMARY KEY,
    community_depleted DOUBLE PRECISION NOT NULL,
    grid_portion DOUBLE PRECISION NOT NULL
);