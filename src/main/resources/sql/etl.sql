SELECT DISTINCT pac_apo.PZN, pac_apo.Langname, sto_db.Wirkstoff, GROUP_CONCAT(sna_db.Name SEPARATOR ', ') AS Substances FROM pac_apo
 JOIN pae_db ON pac_apo.PZN = pae_db.PZN
 JOIN fam_db ON fam_db.Key_FAM = pae_db.Key_FAM
 JOIN fak_db ON fak_db.Key_FAM = fam_db.Key_FAM
 JOIN fai_db ON fai_db.Key_FAM = fak_db.Key_FAM
 JOIN sto_db ON sto_db.Key_STO = fai_db.Key_STO
 JOIN sna_db ON sna_db.Key_STO = sto_db.Key_STO
 WHERE sna_db.Vorzugsbezeichnung = 1 AND sto_db.Wirkstoff = 1
 GROUP BY pac_apo.PZN
 ORDER BY pac_apo.PZN DESC