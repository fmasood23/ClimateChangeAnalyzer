package climatechange;
import java.io.*;
import java.util.*;

//This class reads and writes data to and from a file
public class WeatherIO implements IWeatherIO {
	
	
	//This method reads input from the file
	public ArrayList<ITemperature> readDataFromFile(String fileName){
		ArrayList<ITemperature> document = new ArrayList<ITemperature>();			//This will hold objects created from file values
		try {
			File read = new File(fileName);										
			Scanner in = new Scanner(read);											//I am using a scanner to read the file
			in.nextLine();															//This line allows me to skip the header
		
			while(in.hasNextLine()){	
				String value = in.nextLine();
				String[] parts = value.split(",");									//This line allows me to split up the line into an array along the "," 
			
				String year = parts[1].trim();										//I am storing and trimming the year
				String temp = parts[0].trim();										//I am storing and trimming the temperature
			
				int convertYear = Integer.parseInt(year);							//This converts the year into an integer
				double convertTemp = Double.parseDouble(temp);						//This converts the temperature into a double
			
				Temperature t = new Temperature(parts[3].trim(), 					//This places all the values in the array into a Temperature object
						parts[4].trim(), parts[2].trim(),convertYear, convertTemp);
				document.add(t);													//This object is added to the ArrayList
			}
		}
		catch(FileNotFoundException f) {
			f.printStackTrace();
		}
		return document;															//The ArrayList is returned
	}
	
	
	//This method writes the header of every task in the output file
	public void writeSubjectHeaderInFile(String filename, String subject) throws IOException{
		try {
			File f = new File(filename);			
			FileWriter fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);	//creates the chain of File to FileWriter to PrintWriter which will allow me to write in the file
			
			pw.println(subject);					//writes the subject in the file
			
			pw.close();								//closes the PrintWriter
			fw.close();								//closes the FileWriter
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}

	
	//This writes the data from the ClimateAnalyzer in the file. 
	public void writeDataToFile(String filename, String topic, ArrayList<ITemperature> theWeatherList) throws IOException{
		try {
			FileWriter fw = new FileWriter(filename, true);
			PrintWriter pw = new PrintWriter(fw);											//creates the chain to allow me to write in the file
			
			pw.println(topic);																//first, I am writing the topic. 
			
			for(ITemperature weather: theWeatherList) {										//to access each ITemperature element, I am entering a loop
				double tempC = Math.round(weather.getTemperature(false) * 100.0) / 100.0;	//this stores the temperature in Celsius rounded to two decimals
				double tempF = Math.round(weather.getTemperature(true) * 100.0) / 100.0;	//this stores the temperature in Fahrenheit rounded to two decimals
				int year = weather.getYear();												//this stores the value of the year
				String month = weather.getMonth();											//this stores the value of the month
				String country = weather.getCountry();										//this stores the value of the country name
				String countryCode = weather.getCountry3LetterCode();						//this stores the county code
				
				pw.println(tempC + "(C) " + tempF + "(F)" + "," + year + "," +				
				month + "," + country + "," + countryCode);									//everything is printed with commas to seperate
			}
			
			pw.close();																		//the PrintWriter is closed
			fw.close();																		//the fileWriter is closed
		}
		catch(IOException io) {
			io.printStackTrace();
		}
	}
	
	
	//This writes the data from Part C1 in ClimateAnalyzer in the file. 
		public void writeDataPartC(String filename, String topic, ArrayList<ITemperature> theWeatherList, ArrayList<ITemperature> data, int year) throws IOException{
			try {
				FileWriter fw = new FileWriter(filename, true);
				PrintWriter pw = new PrintWriter(fw);																		//creates the chain to allow me to write in the file
					
				pw.println(topic);																							//first, I am writing the topic. 
				
				for(ITemperature weather: theWeatherList) {																	//to access each ITemperature element, I am entering a loop
					for(ITemperature t: data) {
						
						if(t.getCountry().equals(weather.getCountry())) {							
							if(t.getMonth().equals(weather.getMonth())) {
								if(t.getYear()==year) {																		//This gets the ITemperature element of the second year
									double tempDiffC = Math.abs(t.getTemperature(false) - weather.getTemperature(false));	//This gets the temperature difference in Celcius
									double tempDiffF = Math.abs(t.getTemperature(true)- weather.getTemperature(true));		//This gets the temperature difference in Fahrenheit
									
									double tempC = Math.round(tempDiffC * 100.0) / 100.0;									//this stores the temperature difference in Celsius rounded to two decimals						
									double tempF = Math.round(tempDiffF * 100.0) / 100.0;									//this stores the temperature difference in Fahrenheit rounded to two decimals
									int yearDiff = Math.abs(year - weather.getYear());										//this stores the value of the year
									String month = weather.getMonth();														//this stores the value of the month
									String country = weather.getCountry();													//this stores the value of the country name
									String countryCode = weather.getCountry3LetterCode();									//this stores the county code
					
									pw.println(tempC + "(C) " + tempF + "(F)" + "," + yearDiff + "," +				
											month + "," + country + "," + countryCode);										//everything is printed with commas to seperate
					
								}
							}
						}
					}
				}
				pw.close();																									//the PrintWriter is closed
				fw.close();																									//the fileWriter is closed
			}
			catch(IOException io) {
				io.printStackTrace();
			}
		}
}
