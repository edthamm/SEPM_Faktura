INSERT INTO products VALUES (1, 'Cola', 1.00, 1.5, 'ColaComp', true)
INSERT INTO products VALUES (2, 'Mineralwasser', 0.35, 0.90, 'Roemerquelle', true)
INSERT INTO products VALUES (3, 'Bier', 1.45, 2.30, 'Ottakringer', true)
INSERT INTO products VALUES (4, 'Wein', 1.60, 2.80, 'Weninger', true) 
INSERT INTO products VALUES (5, 'Kaiserschmarn', 1.2, 3.4, 'Kueche', true)
INSERT INTO products VALUES (6, 'Mohr im Hemd', 1.5, 4, 'Do&Co', true)
INSERT INTO products VALUES (7, 'Fritattensuppe', 1.2, 2.20, 'Kueche', true)
INSERT INTO products VALUES (8, 'Leberknoedelsuppe', 1.8, 3, 'Kueche', true)
INSERT INTO products VALUES (9, 'Schweinsbraten', 4.6, 9.50, 'Kueche', true)
INSERT INTO products VALUES (10, 'Schinkenfleckerl', 2.35, 4.5, 'Kueche', true)

INSERT INTO invoice VALUES (1, '2012-01-03', 12.5,'Franz','12:35:14')
INSERT INTO invoice VALUES (2, '2012-01-04', 5.7,'Franz','12:14:16')
INSERT INTO invoice VALUES (3, '2012-01-04', 0.9,'Franz','13:12:56')
INSERT INTO invoice VALUES (4, '2012-01-04', 6.2,'Franz','19:38:37')
INSERT INTO invoice VALUES (5, '2012-01-04', 7.5,'Franz','21:52:00')
INSERT INTO invoice VALUES (6, '2012-01-05', 1.5,'Franz','11:05:10')
INSERT INTO invoice VALUES (7, '2012-01-05', 9.5,'Franz','12:58:27')
INSERT INTO invoice VALUES (8, '2012-01-05', 11.8,'Franz','13:25:44')
INSERT INTO invoice VALUES (9, '2012-01-05', 2.8,'Franz','14:45:11')
INSERT INTO invoice VALUES (10, '2012-01-05', 13.7,'Franz','20:53:28')

INSERT INTO contains VALUES (1,1,1,1)
INSERT INTO contains VALUES (1,9,1,1)
INSERT INTO contains VALUES (1,6,1,1)
INSERT INTO contains VALUES (2,5,1,1)
INSERT INTO contains VALUES (2,3,1,1)
INSERT INTO contains VALUES (3,2,1,1)
INSERT INTO contains VALUES (4,7,2,1)
INSERT INTO contains VALUES (4,2,2,1)
INSERT INTO contains VALUES (5,8,1,1)
INSERT INTO contains VALUES (5,10,1,1)
INSERT INTO contains VALUES (6,1,1,1)
INSERT INTO contains VALUES (7,9,1,1)
INSERT INTO contains VALUES (8,9,1,1)
INSERT INTO contains VALUES (8,3,1,1)
INSERT INTO contains VALUES (9,4,1,1)
INSERT INTO contains VALUES (10,1,2,1)
INSERT INTO contains VALUES (10,7,1,1)
INSERT INTO contains VALUES (10,10,1,1)
INSERT INTO contains VALUES (10,6,1,1)
