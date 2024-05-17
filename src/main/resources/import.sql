INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Bruno', 'Barbieri', '1962-01-12', '{/images/cuochi/Bruno-Barbieri.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Antonino', 'Cannavacciuolo', '1975-04-16', '{/images/cuochi/Antonino-Cannavacciuolo.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Daniel', 'Boulud', '1955-03-25', '{/images/cuochi/Daniel-Boulud.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Enrico', 'Bartolini', '1979-03-20', '{/images/cuochi/Enrico-Bartolini.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Enrico', 'Crippa', '1971-11-15', '{/images/cuochi/Enrico-Crippa.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Heinz', 'Beck', '1963-11-03', '{/images/cuochi/Heinz-Beck.jpeg}');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, urls_images) VALUES(nextval('cuoco_seq'), 'Riccardo', 'Monco', '1969-02-19', '{/images/cuochi/Riccardo-Monco.jpeg}');

INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Carbonara Vegetariana', 'La nostra Carbonara Vegetarian è una rivisitazione leggera e gustosa del classico romano. Spaghetti al dente si fondono con una crema vellutata di uova e pecorino, arricchita da croccanti cubetti di zucchine e carote. Un tocco di pepe nero fresco completa il piatto, offrendo un equilibrio perfetto tra sapore e leggerezza.', (SELECT id FROM cuoco WHERE nome = 'Bruno' AND cognome = 'Barbieri'), '{/images/ricette/CarbonaraVegetariana.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Dolce Prato', 'Il Dolce Prato è un dessert raffinato che celebra i sapori della tradizione toscana. Una base soffice di pan di spagna è arricchita da una crema di ricotta e frutti di bosco freschi. La nota di vin santo conferisce un tocco di eleganza, trasformando ogni morso in un esperienza sensoriale unica.', (SELECT id FROM cuoco WHERE nome = 'Antonino' AND cognome = 'Cannavacciuolo'), '{/images/ricette/DolcePrato.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Panna Cotta Matisse', 'La Panna Cotta Matisse è un dolce che unisce arte e gusto. La panna cotta, morbida e setosa, è decorata con uno strato di gelatina ai frutti di bosco che crea un contrasto di colori e sapori. Una spolverata di fiori commestibili aggiunge un tocco di raffinatezza, rendendo questo dessert un vero capolavoro.', (SELECT id FROM cuoco WHERE nome = 'Daniel' AND cognome = 'Boulud'), '{/images/ricette/PannaCottaMatisse.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Papouitte di Pesce', 'La Papouitte di Pesce è un piatto che cattura l essenza del mare. Filetti di pesce freschissimi sono avvolti in una leggera sfoglia e cotti al vapore per preservarne la delicatezza. Un emulsione di agrumi e finocchio completa il piatto, esaltando la freschezza e la semplicità degli ingredienti.', (SELECT id FROM cuoco WHERE nome = 'Enrico' AND cognome = 'Bartolini'), '{/images/ricette/PapouittePesce.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Uovo Cocotte', 'Uovo Cocotte è un antipasto elegante e raffinato. L uovo, cotto alla perfezione in un bagno di panna e tartufo nero, è servito con una croccante baguette tostata. Ogni boccone è un esplosione di sapori, dove la cremosità dell uovo si sposa con l intensità del tartufo.', (SELECT id FROM cuoco WHERE nome = 'Enrico' AND cognome = 'Crippa'), '{/images/ricette/UovoCocotte.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Vitello', 'Il nostro Vitello è un piatto che celebra la carne tenera e succosa, cotta a bassa temperatura per esaltarne il sapore. Accompagnato da una salsa di riduzione al vino rosso e contorni di verdure di stagione, questo piatto offre un equilibrio perfetto tra eleganza e tradizione.', (SELECT id FROM cuoco WHERE nome = 'Heinz' AND cognome = 'Beck'), '{/images/ricette/Vitello.jpeg}');
INSERT INTO ricetta (id, nome, descrizione, cuoco_id, urls_images) VALUES(nextval('ricetta_seq'), 'Vitello Tonnato', 'Il nostro Vitello Tonnato è un classico della cucina piemontese, rivisitato in chiave moderna. Fette sottili di vitello rosato sono avvolte in una salsa cremosa al tonno e capperi, con un tocco di limone per freschezza. Un piatto elegante e raffinato, perfetto per ogni occasione.', (SELECT id FROM cuoco WHERE nome = 'Riccardo' AND cognome = 'Monco'), '{/images/ricette/VitelloTonnato.jpeg}');

INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Spaghetti');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Zucchine');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Carote');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Pecorino Romano');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Uova');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Pepe Nero');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Pan di Spagna');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Ricotta');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Frutti di Bosco');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Vin Santo');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Gelatina');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Fiori Commestibili');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Filetti di Pesce');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Agrumi');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Finocchio');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Tartufo Nero');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Baguette');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Vitello');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Vino Rosso');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Verdure di Stagione');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Tonno');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Panna');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Capperi');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Limone');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Basilico');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Olio di Oliva');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Aglio Nero');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Salsa di Soia');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Patate');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Pangrattato');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Rosmarino');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Scorza di Limone');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Menta');
INSERT INTO ingrediente (id, nome) VALUES(nextval('ingrediente_seq'), 'Aceto Balsamico');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Pan di Spagna'), (SELECT id FROM ricetta WHERE nome = 'Dolce Prato'), '200 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Ricotta'), (SELECT id FROM ricetta WHERE nome = 'Dolce Prato'), '250 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Frutti di Bosco'), (SELECT id FROM ricetta WHERE nome = 'Dolce Prato'), '150 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Vin Santo'), (SELECT id FROM ricetta WHERE nome = 'Dolce Prato'), '50 ml');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Panna'), (SELECT id FROM ricetta WHERE nome = 'Panna Cotta Matisse'), '500 ml');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Gelatina'), (SELECT id FROM ricetta WHERE nome = 'Panna Cotta Matisse'), '10 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Frutti di Bosco'), (SELECT id FROM ricetta WHERE nome = 'Panna Cotta Matisse'), '150 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Fiori Commestibili'), (SELECT id FROM ricetta WHERE nome = 'Panna Cotta Matisse'), 'q.b.');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Filetti di Pesce'), (SELECT id FROM ricetta WHERE nome = 'Papouitte di Pesce'), '400 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Agrumi'), (SELECT id FROM ricetta WHERE nome = 'Papouitte di Pesce'), '100 ml');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Finocchio'), (SELECT id FROM ricetta WHERE nome = 'Papouitte di Pesce'), '1');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Uova'), (SELECT id FROM ricetta WHERE nome = 'Uovo Cocotte'), '2');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Panna'), (SELECT id FROM ricetta WHERE nome = 'Uovo Cocotte'), '100 ml');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Tartufo Nero'), (SELECT id FROM ricetta WHERE nome = 'Uovo Cocotte'), 'q.b.');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Baguette'), (SELECT id FROM ricetta WHERE nome = 'Uovo Cocotte'), '1');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Vitello'), (SELECT id FROM ricetta WHERE nome = 'Vitello'), '500 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Vino Rosso'), (SELECT id FROM ricetta WHERE nome = 'Vitello'), '200 ml');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Verdure di Stagione'), (SELECT id FROM ricetta WHERE nome = 'Vitello'), '200 gr');

INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Vitello'), (SELECT id FROM ricetta WHERE nome = 'Vitello Tonnato'), '400 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Tonno'), (SELECT id FROM ricetta WHERE nome = 'Vitello Tonnato'), '150 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Capperi'), (SELECT id FROM ricetta WHERE nome = 'Vitello Tonnato'), '20 gr');
INSERT INTO riga_ricetta (id, ingrediente_id, ricetta_id, quantita) VALUES (nextval('riga_ricetta_seq'), (SELECT id FROM ingrediente WHERE nome = 'Limone'), (SELECT id FROM ricetta WHERE nome = 'Vitello Tonnato'), '1');


