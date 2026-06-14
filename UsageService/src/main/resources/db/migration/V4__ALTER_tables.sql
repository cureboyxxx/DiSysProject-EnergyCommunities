ALTER TABLE EnergyUsage ALTER COLUMN community_produced TYPE double precision;
ALTER TABLE EnergyUsage ALTER COLUMN community_used TYPE double precision;
ALTER TABLE EnergyUsage ALTER COLUMN grid_used TYPE double precision;

ALTER TABLE CurrentPercentage ALTER COLUMN community_depleted TYPE double precision;
ALTER TABLE CurrentPercentage ALTER COLUMN grid_portion TYPE double precision;