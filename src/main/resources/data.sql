INSERT INTO page_definitions (slug, title)
VALUES ('welcome', 'Welcome to Archweave');

INSERT INTO widget_definitions (page_id, type, content, display_order)
SELECT
    id,
    'HTML',
    '<h1>Welcome to Archweave</h1>',
    1
FROM page_definitions
WHERE slug = 'welcome';

INSERT INTO widget_definitions (page_id, type, content, display_order)
SELECT
    id,
    'HTML',
    '<p>Archweave renders this HTML from database-defined widgets.</p>',
    2
FROM page_definitions
WHERE slug = 'welcome';