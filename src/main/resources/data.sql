TRUNCATE TABLE song, playlist, playlist_song;

--Ylempi komento resetoi tietokannan aina kun sovellus uudelleenkäynnistyy, tämä ei tietenkään olisi tuotannossa kovin hyvä
-- Nämä biisit lisätään tietokantaan sovelluksen käynnistyessä

INSERT INTO song (name, artist, album, genre, release_year, duration, language, composer, lyrics_url) VALUES 
('Over', 'Playboi Carti', 'Whole Lotta Red', 'Rap', 2020, '3:30', 'English', 'Playboi Carti', 'http://example.com/lyrics1'),
('Kaksoiselämää', 'Juice Leskinen', 'Yölento', 'Suomirock', 1986, '5:08', 'Finnish', 'Juice Leskinen', 'http://example.com/lyrics2'),
('Sweden', 'C418', 'Minecraft - Volume Alpha', 'Ambient', 2011, '3:35', 'English', 'C418', 'http://example.com/lyrics3');

INSERT INTO playlist (name) VALUES
('Playlist 1'), 
('Playlist 2'),
('Rap Songs'),
('Pop');