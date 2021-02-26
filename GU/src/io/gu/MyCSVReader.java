package io.gu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MyCSVReader {
	
// Variables
	File myObject;
	Scanner myReader;
	DatabaseConnection dbc;
	String row [] = new String [400000];
	boolean createFile = false;
	
	int gesamtOhne;
	int gesamtMit;
	int gesUOBeginn;
	int gesUMBeginn;
	int alreadyHadBday;
	long difference;
	long diff_bday;
	
	String sql;
	String sql2;
	String sql3;
	
	LocalDate date = LocalDate.now();
	Date dnow = new Date();
	SimpleDateFormat of = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyy");
	
// my actual Team
	// [0] Erfahrung, [1] Zweikampf, [2] Spielaufbau, [3] Passspiel, [4] Torschuss, [5] GesamtOhne, [6] GesamtMit, [7] Alter, [8] FirstDBEntry
	int _515178738_Geneste [] = {100, 100, 65, 43, 46, 254, 354, 28, 20210207};
	int _531374955_Persson [] = {90, 39, 68, 92, 97, 296, 386, 27, 20210207};
	int _546138663_Mehmke [] = {18,94,65,81,55,295,313,23, 20210210};
	int _549777929_Bäre [] = {87,80,74,66,39,259,346,22,20210207};
	int _549831723_Kalil [] = {47,89,63,55,38,245,292,22,20210207};
	int _549862030_Hübner [] = {60,31,74,91,71,267,327,22,20210207};
	int _549953049_Maifredi [] = {50,31,77,73,74,255,305,22,20210207};
	int _549968065_Beck [] = {51,85,59,61,36,241,292,21,20210207};
	int _550247731_Niciowski [] = {58,25,75,48,87,235,293,21,20210207};
	int _552773530_Modzik [] = {46,33,60,83,69,245,291,21,20210207};
	int _552835215_Teymoorpanah [] = {49,34,79,84,65,262,311,21,20210207};
	int _552921731_Lichte [] = {28,73,59,78,53,263,291,20,20210207};
	int _552980589_Ahmann [] = {27,27,79,51,73,230,257,20,20210207};
	int _552999905_Stammer [] = {54,73,73,36,30,212,266,20,20210207};
	int _553012407_Danker [] = {44,29,78,78,49,234,278,20,20210207};
	int _555727179_Lindemann [] = {41,31,79,80,43,233,274,20,20210207};
	int _555766053_Backo [] = {22,26,71,64,40,201,223,19,20210207};
	int _555853663_Greve [] = {33,20,49,82,59,210,243,19,20210207};
	int _555929849_Furse [] = {100,100,100,100,100,400,500,30,20210207};
	int _558232512_Rathsack [] = {14,70,53,22,14,159,173,18,20210207};
	int _558332013_Reihmann [] = {18,19,34,74,45,172,190,19,20210207};
	int _558414694_Bakewell [] = {100,73,100,46,69,288,388,28,20210207};
	int _558465401_Mahmad [] = {100,42,0,0,0,42,142,30,20210207};
	int _560709741_Krug [] = {8,26,48,91,40,205,213,18,20210207};
	int _560722031_Engelke [] = {8,74,22,14,11,121,129,17,20210207};
	int _560742875_Tode [] = {5,70,24,12,16,122,127,17,20210207};
	int _560801823_Supek [] = {82,75,96,71,68,310,392,20,20210221};
	int _560819922_Winnerstrand [] = {78,64,78,75,96,313,391,20,20210226};
	String Spielerliste [] = {"_515178738_Geneste", "_531374955_Persson", "_546138663_Mehmke", "_549777929_Bäre", "_549831723_Kalil", "_549862030_Hübner", "_549953049_Maifredi", "_549968065_Beck", "_550247731_Niciowski", "_552773530_Modzik", "_552835215_Teymoorpanah", "_552921731_Lichte", "_552980589_Ahmann", "_552999905_Stammer", "_553012407_Danker", "_555727179_Lindemann", "_555766053_Backo", "_555853663_Greve", "_555929849_Furse", "_558232512_Rathsack", "_558332013_Reihmann", "_558414694_Bakewell", "_558465401_Mahmad", "_560709741_Krug", "_560722031_Engelke", "_560742875_Tode", "_560801823_Supek", "_560819922_Winnerstrand"};
	
	/* Aufbau der GU-CSV-Datei ----->>> neu zählen
	 * [0] = ID / [1] = Nr / [2] = Vorname / [3] = Nachname / [4] = Training / [5] = Land 
	 * [6] = Stärke / [7] = Position / [8] = Form / [9] = Frische / [10] = Kondition 
	 * [11] = Erfahrung / [12] = Torwart / [13] = Zweikampf / [14] = Spielaufbau / [15] = Passspiel 
	 * [16] = Torschuss / [17] = Talent-Level / [18] = Alter / [19] = Größe / [20] = Charakter 
	 * [21] = Geburtstag / [22] = Fuß / [23] = Gehalt / [24] = Einsätze / [25] = Tore Saison
	 * [26] = Tore Gesamt / [27] = Marktwert / [28] = gelbe K Saison / [29] = gelbe K Gesamt / [30] = rote K Saison / [31] = rote K Gesamt 
	 */
	
	public MyCSVReader(String source) throws ParseException {
		writeSql(source);
	}
	
	public void writeSql(String source) throws ParseException {
		try(BufferedReader reader = new BufferedReader(new FileReader(source))) {
			
			int count=0;
			String line;
			// Die ersten beiden Zeilen überspringen
			reader.readLine();
			reader.readLine();
			
			while((line = reader.readLine()) != null) {	
				// Reset variables
				String dbName = "";
				gesamtOhne = 0;
				gesamtMit = 0;
				gesUOBeginn = 0;
				gesUMBeginn = 0;
				difference = 0;
				alreadyHadBday = 0;
				sql = "";
				sql2 = "";
				sql3 = "";

				if (count == Spielerliste.length) {
					return;
				}
					
				row = line.split(";");
				
				// Sonderfälle abfangen
				if (row[3].equals("Ba?ko")) {
					row[3] = "Backo";
				}
				
				if (row[3].equals("Teymoor panah")) {
					row[3] = "Teymoorpanah";
				}
				
				dbName = row[0] + "_" + row[3]; 
				if (dbName.equals("515178738_Geneste ")){
					dbName = "515178738_Geneste";
				}
				
				gesamtOhne = Integer.parseInt(row[13]) + Integer.parseInt(row[14]) + Integer.parseInt(row[15]) + Integer.parseInt(row[16]);
				gesamtMit = gesamtOhne + Integer.parseInt(row[11]);
				
				// Berechnung der Gesamtwerte
				calculateValues(dbName, row);		
				
				sql = "INSERT INTO public.\"" + dbName 
						+ "\" (\"Erfahrung\", \"Zweikampf\", \"Spielaufbau\", \"Passspiel\", \"Torschuss\", \"GesamtOhne\", \"GesamtMit\", \"Alter\", \"Marktwert\", \"Datum\", \"Position\") "
						+ "VALUES(" + row[11] + "," + row[13] + "," + row[14] + "," + row[15] + "," + row[16] + "," + gesamtOhne + "," + gesamtMit + "," + row[19] + "," + row[28] + "," + "now()" + ",'" + row[7] + "');";
				
				sql2 = "UPDATE public.\"Vergleichsportal\""
						+ " SET \"Name\"='" + dbName + "', \"Age\"=" + row[19] + ", \"Erfahrung\"=" + row[11] + ", \"Zweikampf\"=" + row[13] + ", \"Spielaufbau\"=" + row[14] +", \"Passspiel\"=" + row[15] + ", \"Torschuss\"=" + row[16] + ", \"GesamtOhne\"=" + gesamtOhne + ", \"GesamtMit\"=" + gesamtMit + ", \"GesUOBeginn\"=" + gesUOBeginn + ", \"GesUMBeginn\"=" + gesUMBeginn + ", \"Datum\"= now(), \"Zeitraum\"=" + difference + ", \"HatteGeburtstag\"=" + alreadyHadBday + ""
						+ " WHERE \"Name\" = '" + dbName + "';";
				
				sql3 = "INSERT INTO public.\"Charaktere\""
						+ "(\"Name\", \"Charakter\")"
						+ " VALUES('" + row[3] + "','" + row[21] + "');";
				
				dbc = new DatabaseConnection(sql);
				dbc = new DatabaseConnection(sql2);
				dbc = new DatabaseConnection(sql3);				
				
				count++;
			}			
			
			JOptionPane.showMessageDialog(null, "Daten erfolgreich eingelesen", "Hinweis", JOptionPane.INFORMATION_MESSAGE, null);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String calculateValues(String dbName, String row[]) throws ParseException {
		Integer value;
		
		switch("_" + dbName) {
		case "_515178738_Geneste":
			gesUOBeginn = gesamtOhne - _515178738_Geneste[5];
			gesUMBeginn = gesamtMit - _515178738_Geneste[6];
			
			value = _515178738_Geneste[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_531374955_Persson":
			gesUOBeginn = gesamtOhne - _531374955_Persson[5];
			gesUMBeginn = gesamtMit - _531374955_Persson[6];
			
			value = _531374955_Persson[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);

			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_546138663_Mehmke":
			gesUOBeginn = gesamtOhne - _546138663_Mehmke[5];
			gesUMBeginn = gesamtMit - _546138663_Mehmke[6];
			
			value = _546138663_Mehmke[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);

			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_549777929_Bäre":
			gesUOBeginn = gesamtOhne - _549777929_Bäre[5];
			gesUMBeginn = gesamtMit - _549777929_Bäre[6];
		
			value = _549777929_Bäre[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_549831723_Kalil":
			gesUOBeginn = gesamtOhne - _549831723_Kalil[5];
			gesUMBeginn = gesamtMit - _549831723_Kalil[6];
			
			value = _549831723_Kalil[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_549862030_Hübner":
			gesUOBeginn = gesamtOhne - _549862030_Hübner[5];
			gesUMBeginn = gesamtMit - _549862030_Hübner[6];
			
			value = _549862030_Hübner[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_549953049_Maifredi":
			gesUOBeginn = gesamtOhne - _549953049_Maifredi[5];
			gesUMBeginn = gesamtMit - _549953049_Maifredi[6];
			
			value = _549953049_Maifredi[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_549968065_Beck":
			gesUOBeginn = gesamtOhne - _549968065_Beck[5];
			gesUMBeginn = gesamtMit - _549968065_Beck[6];
			
			value = _549968065_Beck[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_550247731_Niciowski":
			gesUOBeginn = gesamtOhne - _550247731_Niciowski[5];
			gesUMBeginn = gesamtMit - _550247731_Niciowski[6];
			
			value = _550247731_Niciowski[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_552773530_Modzik":
			gesUOBeginn = gesamtOhne - _552773530_Modzik[5];
			gesUMBeginn = gesamtMit - _552773530_Modzik[6];
			
			value = _552773530_Modzik[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_552835215_Teymoorpanah":
			gesUOBeginn = gesamtOhne - _552835215_Teymoorpanah[5];
			gesUMBeginn = gesamtMit - _552835215_Teymoorpanah[6];
			
			value = _552835215_Teymoorpanah[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_552921731_Lichte":
			gesUOBeginn = gesamtOhne - _552921731_Lichte[5];
			gesUMBeginn = gesamtMit - _552921731_Lichte[6];
			
			value = _552921731_Lichte[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_552980589_Ahmann":
			gesUOBeginn = gesamtOhne - _552980589_Ahmann[5];
			gesUMBeginn = gesamtMit - _552980589_Ahmann[6];
			
			value = _552980589_Ahmann[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_552999905_Stammer":
			gesUOBeginn = gesamtOhne - _552999905_Stammer[5];
			gesUMBeginn = gesamtMit - _552999905_Stammer[6];
			
			value = _552999905_Stammer[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_553012407_Danker":
			gesUOBeginn = gesamtOhne - _553012407_Danker[5];
			gesUMBeginn = gesamtMit - _553012407_Danker[6];
			
			value = _553012407_Danker[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_555727179_Lindemann":
			gesUOBeginn = gesamtOhne - _555727179_Lindemann[5];
			gesUMBeginn = gesamtMit - _555727179_Lindemann[6];
			
			value = _555727179_Lindemann[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_555766053_Backo":
			gesUOBeginn = gesamtOhne - _555766053_Backo[5];
			gesUMBeginn = gesamtMit - _555766053_Backo[6];
			
			value = _555766053_Backo[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_555853663_Greve":
			gesUOBeginn = gesamtOhne - _555853663_Greve[5];
			gesUMBeginn = gesamtMit - _555853663_Greve[6];
			
			value = _555853663_Greve[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_555929849_Furse":
			gesUOBeginn = gesamtOhne - _555929849_Furse[5];
			gesUMBeginn = gesamtMit - _555929849_Furse[6];
			
			value = _555929849_Furse[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_558232512_Rathsack":
			gesUOBeginn = gesamtOhne - _558232512_Rathsack[5];
			gesUMBeginn = gesamtMit - _558232512_Rathsack[6];
			
			value = _558232512_Rathsack[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_558332013_Reihmann":
			gesUOBeginn = gesamtOhne - _558332013_Reihmann[5];
			gesUMBeginn = gesamtMit - _558332013_Reihmann[6];
			
			value = _558332013_Reihmann[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_558414694_Bakewell":
			gesUOBeginn = gesamtOhne - _558414694_Bakewell[5];
			gesUMBeginn = gesamtMit - _558414694_Bakewell[6];
			
			value = _558414694_Bakewell[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_558465401_Mahmad":
			gesUOBeginn = gesamtOhne - _558465401_Mahmad[5];
			gesUMBeginn = gesamtMit - _558465401_Mahmad[6];
			
			value = _558465401_Mahmad[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_560709741_Krug":
			gesUOBeginn = gesamtOhne - _560709741_Krug[5];
			gesUMBeginn = gesamtMit - _560709741_Krug[6];
			
			value = _560709741_Krug[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_560722031_Engelke":
			gesUOBeginn = gesamtOhne - _560722031_Engelke[5];
			gesUMBeginn = gesamtMit - _560722031_Engelke[6];
			
			value = _560722031_Engelke[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_560742875_Tode":
			gesUOBeginn = gesamtOhne - _560742875_Tode[5];
			gesUMBeginn = gesamtMit - _560742875_Tode[6];
			
			value = _560742875_Tode[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_560801823_Supek":
			gesUOBeginn = gesamtOhne - _560801823_Supek[5];
			gesUMBeginn = gesamtMit - _560801823_Supek[6];
			
			value = _560801823_Supek[8];			
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		case "_560819922_Winnerstrand":
			gesUOBeginn = gesamtOhne - _560819922_Winnerstrand[5];
			gesUMBeginn = gesamtMit - _560819922_Winnerstrand[6];
			
			value = _560819922_Winnerstrand[8];
			difference = daysBetween(value.toString(), dnow);
			
			alreadyHadBday = checkBday(row[22], dnow);
			
			return gesUOBeginn + ";" + gesUMBeginn + ";" + difference + ";" + alreadyHadBday;
		default:
			return "Spieler nicht gefunden!";
		}
	}

	public long daysBetween(String first, Date now) throws ParseException {
		Date dfirst = of.parse(first);
		long difference = (dfirst.getTime() - now.getTime()) / 86400000; // Number of Milliseconds between 2 days
		return Math.abs(difference); // absolute number 
	}
	
	public int checkBday(String first, Date now) throws ParseException {
		Date bday_date = sf.parse(first.replace(".",  "") + "2021"); // using sf Parser here because DateFormat is ddMM
		if (((bday_date.getTime() - dnow.getTime()) / 86400000) <= 0) {
			alreadyHadBday = 1; // true = Player had his birthday
		}
		return alreadyHadBday;
	}
	
}
