CREATE TABLE EnergyUsage(
    hour TIMESTAMP PRIMARY KEY,
    community_produced NUMERIC(8, 3) NOT NULL,
    community_used NUMERIC(8, 3) NOT NULL,
    grid_used NUMERIC(8, 3) NOT NULL
);
CREATE TABLE CurrentPercentage(
    hour TIMESTAMP PRIMARY KEY,
    community_depleted NUMERIC(5, 2) NOT NULL,
    grid_portion NUMERIC(5, 2) NOT NULL
);