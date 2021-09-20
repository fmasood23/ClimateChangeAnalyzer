package climatechange;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//This class gets certain Temperatures from the file
public class ClimateAnalyzer extends TreeSet<ITemperature> implements IClimateAnalyzer{
	
	private String file;		//This represents the file to read in which methods use 
	
	
	public ClimateAnalyzer(String file) {
		this.file = file;
	}
	
	
	//this method converts the month from an integer value into one in words to match the input file
	public String getMonthInWords(int month) {
		String months =""; 													//this will store the string value
		
		HashMap<Integer, String> data = new HashMap<Integer, String>();		//this hashmap will connect the Integer and String values for the month
		String[] words = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", 	//This is an array of all the months in words
				"Aug", "Sep", "Oct", "Nov", "Dec"};
		
		for(int i=0; i<12; i++) {											//this places the String words and integer words into the hashmap
			data.put(i+1, words[i]);
		}
		for(Integer value: data.keySet()) {									//this traverses through the integer values
			if(value==month) {												//this checks if the value is equal to the input parameter
				months = data.get(value);									//this gets the corresponding string
			}
		}
		return months;														//the string version of the month is returned
	}
	
	
	//This gets the lowest temperature, given the country and month
	//Task A1
	public ITemperature getLowestTempByMonth(String country, int month) {
		String wordMonth = getMonthInWords(month);							//This gets the month in words, not numbers
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);	//This stores data from the file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {									//This loops through all the data from the file
			if(t.getCountry().equals(country)) {							//This limits the range to only a certain country
				if(t.getMonth().equals(wordMonth)) {						//This limits it further to only a certain month
					sort.add(t);											//Objects with that country and month are added to another ArrayList
				}
			}
		}		
		
		ITemperature min = sort.get(0); 									//This sets the minimum value to the first object in the ArrayList
		for(int i=1; i<sort.size(); i++) {	
			ITemperature current = sort.get(i);
			if(min.getTemperature(false)>current.getTemperature(false)) {   //This checks to see if the current temperature is less than the minimum
				min = current;												//If that is true, it sets the minimum to the current value
			}	
		}	
		
		return min;															//the Object with the lowest temperature is returned
	}

	
	//This gets the highest temperature, given the country and month
	//Task A1
	public ITemperature getHighestTempByMonth(String country, int month) {
		String wordMonth = getMonthInWords(month);							//This converts the integer month parameter to a String
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);	//This allows the user to read from the input file and store contents in an ArrayList
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();		 
		
		for(ITemperature t: dataFromDoc) {									//This traces through every element from the input file
			if(t.getCountry().equals(country)) {							//This filters the content according to country name
				if(t.getMonth().equals(wordMonth)) {						//This filters the content according to month
					sort.add(t);											//Only elements with the certain country name and month are added to this ArrayList
				}
			}
		}		
		
		ITemperature max = sort.get(0); 									//This sets the max to the first element of the ArrayList
		for(int i=1; i<sort.size(); i++) {
			ITemperature current = sort.get(i);								//This stores the current object of ITemperature
			if(max.getTemperature(false)<current.getTemperature(false)){	//This checks if the temperature of the max is less than the temperature of the current object
				max = current;												//If this is true, the max is changed to equal the current object
			}
		}	
		
		return max;															//The element with the highest temperature is returned.
	}

	
	//This method gets the lowest temperature, given country and year
	//Task A2
	public ITemperature getLowestTempByYear(String country, int year) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);	//This allows the user to read from the input file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {									//This loops through every element from the input file
			if(t.getCountry().equals(country)) {							//This filters content according to the country
				if(t.getYear()==(year)) {									//This filters content according to year
					sort.add(t);											//Only objects of with the appropriate country name and year are added to this ArrayList
				}
			}
		}
		
		ITemperature min = sort.get(0); 									//This sets the minimum to the first value in sort
		for(int i=1; i<sort.size(); i++) {
			ITemperature current = sort.get(i);								//This represents the current value
			if(min.getTemperature(false)>current.getTemperature(false)){ 	//This checks if the temperature of the minimum is greater than the temperature of the current
				min = current;												//If that is the case, the minimum is set to equal the current
			}
		}
		return min;															//The object of the lowest temperature is returned
	}

	
	//This method gets the highest temperature, given country and year
	//Task A2
	public ITemperature getHighestTempByYear(String country, int year) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);	//This allows the user to read the input file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {									//This loops through every element of the input file
			if(t.getCountry().equals(country)) {							//This filters data according to county name
				if(t.getYear()==(year)) {									//This filters data according to the year
					sort.add(t);											//Only objects of the appropriate country and year are added to the sort ArrayList
				}
			}
		}
		
		ITemperature max = sort.get(0); 									//The max is set to the first value of the ArrayList
		for(int i=1; i<sort.size(); i++) {
			ITemperature current = sort.get(i);								//This represents the current value
			if(max.getTemperature(false)<current.getTemperature(false)){	//This checks if the temperature of the max is less than the temperature of the current object
				max = current;												//If this is true, the max is set to equal the current object
			}
		}
		return max;															//The object with the largest temperature is returned
	}

	
	//This method gets all the temperatures for a certain country within a range
	//Task A3
	public TreeSet<ITemperature> getTempWithinRange(String country, double rangeLowTemp, double rangeHighTemp) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);									//This gets data from the input file
		TreeSet<ITemperature> sort = new TreeSet<ITemperature>();
		
		for(ITemperature t:dataFromDoc) {																	//This allows me to loop through each element in the input file
			if(t.getCountry().equals(country)) {															//This filters content according to country
				if(t.getTemperature(false)>=rangeLowTemp && t.getTemperature(false)<=rangeHighTemp) {		//This line checks to see if the temperature is within the range. It is inclusive of bounds
					sort.add(t);																			//If this is the case, the value is added to the TreeSet
				}
			}
		}
		return sort;																						//A TreeSet of values from a specific country with temperatures between a range is returned
	}

	
	//This method gets the lowest temperature given the country
	//Task A4
	public ITemperature getLowestTempYearByCountry(String country)  {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets all data from the input file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {										//This loops through every object from the input file
			if(t.getCountry().equals(country)) {								//This filters results according to country
				sort.add(t);													//Objects with the specific country are added to this ArrayList
			}
		}
		
		ITemperature min = sort.get(0); 										//The min value is set to the first object in the ArrayList
		for(int i=1; i<sort.size(); i++) {
			ITemperature current = sort.get(i);									//This represents the current value
			if(min.getTemperature(false)>current.getTemperature(false)) {		//This checks to see if the min temperature is greater than the current
				min = current;													//If this is the case, the minimum is set to equal the current value
			}
		}
		return min;																//The value with the smallest temperature in the specified country is returned
	}

	
	//This method gets the highest temperature given the country
	//Task A4
	public ITemperature getHighestTempYearByCountry(String country) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets all the data from the input file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {										//This loops through every object in the input file
			if(t.getCountry().equals(country)) {								//This filters the data according to country
				sort.add(t);													//If objects hold the specific country name, they are added to the ArrayList
			}
		}
		
		ITemperature max = sort.get(0); 										//This sets the max equal to the first object in the ArrayList
		for(int i=1; i<sort.size(); i++) {
			ITemperature current = sort.get(i);									//This represents the current object
			if(max.getTemperature(false)<current.getTemperature(false)){		//This checks to see if the temperature of the max object is less than the current
				max = current;													//If this is the case, the max is set to equal the current
			}
		}
		return max;																//The value with the largest temperature in the specified country is returned
	}


	//This method gets the top ten lowest temperatures, given a month
	//Task B1
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(int month) {
		WeatherIO obj = new WeatherIO();
		String wordMonth = getMonthInWords(month);								//This converts the month from integer to String
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets data from the input file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {										//This loops through every object in the input file
			if(t.getMonth().equals(wordMonth)) {								//This filters objects by month
				sort.add(t);													//If an object's month equals specific month, it is added the to the ArrayList
			}
		}
		
		Collections.sort(sort);													//This sorts the list in terms of temperature so it is from lowest to highest
		
		ArrayList<ITemperature> lowestTemp = new ArrayList<ITemperature>(10);   //This creates a new ArrayList that has a size of ten
		ArrayList<String> countryNames = new ArrayList<String>();				//This will hold country names to assist avoiding repeats of countries
		
		int count=0;															//This represents a count of how many objects are added to the lowestTemp ArrayList, it's initialized to 0
		int i=0;																//This represents a value of how many times the loop has cycled, i's initialized to 0
		while(i<sort.size()&& count<10) {										//The loop will continue to cycle if i is less than the length of the sort ArrayList and if the count is less than 10
			ITemperature current = sort.get(i);									//This gets the current value of sort ArrayList
			if(!(countryNames.contains(current.getCountry()))) {				//This checks to see if the country name of the current value is not in the country name ArrayList
				lowestTemp.add(current);										//If this is true, I am adding the current value to the ArrayList of lowest temperatures
				countryNames.add(current.getCountry());							//I am also adding the country name to the country names ArrayList
				count++;														//count is incremented
			}
			i++;																//i is incremented
		}
		
		return lowestTemp;														//The ArrayList with the ten lowest temperatures with unique countries is returned
	}

	
	//This method gets the top ten highest temperatures, given a month
	//Task B1
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(int month) {
		WeatherIO obj = new WeatherIO();
		String wordMonth = getMonthInWords(month);								//This converts the month from an Integer to a String
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets input data from the file
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature t: dataFromDoc) {										//This loops through all the data from the input file
			if(t.getMonth().equals(wordMonth)) {								//This filters content according to month
				sort.add(t);													//If the object has the specified month, it is added to the ArrayList
			}
		}
		
		Collections.sort(sort);													//This sorts the ArrayList by temperature from lowest to highest
		
		ArrayList<ITemperature> highestTemp = new ArrayList<ITemperature>(10);	//This creates a new ArrayList of size ten to hold the highest temperatures
		ArrayList<String> countryNames = new ArrayList<String>();				//This will hold country names to assist avoiding repeats of countries
		
		int count=0;															//This represents a count of how many objects are added to the array of highest temperatures. It is initialized to 0
		int i=sort.size()-1;													//This represents the starting place of where to loop. I am starting at the end of the sort ArrayList
		while(i>0&& count<10) {													//The ArrayList loops through if i is greater than 0 and count is less than 10
			ITemperature current = sort.get(i);									//This stores the current value
			if(!(countryNames.contains(current.getCountry()))) {				//This checks to see if the country name of the current value is not in the country name ArrayList
				highestTemp.add(current);										//If this is true, the current value is added to the highest temperatures ArrayList
				countryNames.add(current.getCountry());							//The name of the country is added to the country ArrayList
				count++;														//count is incremented
			}
			i--;																//i is decremented 
		}
		Collections.sort(highestTemp);											//The ArrayList is sorted again so that it is ordered from lowest to highest temperatures
		
		return highestTemp;														//An ArrayList with the top ten highest temperatures, given a month, is returned
	}

	
	//This method gets the top ten lowest temperature for all countries
	//Task B2
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp() {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets the input data from the file
		
		Collections.sort(dataFromDoc);											//The input data is sorted from lowest to highest temperatures
		
		ArrayList<ITemperature> lowestTemp = new ArrayList<ITemperature>(10);	//A similar implementation from Task B1-1 is used here. This stores the lowest temperatures
		ArrayList<String> countryNames = new ArrayList<String>();				//This will hold the country names to prevent duplicate countries
		
		int count=0;															//This represents the count of how many objects are added to the lowest temperature ArrayList
		int i=0;																//This helps keep track of the index
		while(i<dataFromDoc.size()&& count<10) {								//The loop will continue to cycle while i is less than the length of the ArrayList of file data and while count is less than 10			
			ITemperature current = dataFromDoc.get(i);							//This gets the current object value
			if(!(countryNames.contains(current.getCountry()))) {				//This checks to see if the country name of the current value is not in the country name ArrayList
				lowestTemp.add(current);										//If that is true, the object is added to the lowest temperature ArrayList
				countryNames.add(current.getCountry());							//The name of the country is added to the country names ArrayList
				count++;														//count is incremented
			}
			i++;																//i is incremented
		}
		
		return lowestTemp;														//An ArrayList with the top ten lowest temperatures with unique countries is returned
	}

	
	//This method gets the top ten highest temperature for all countries
	//Task B2
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp() {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets data from the file
		
		Collections.sort(dataFromDoc);											//The data is sorted from lowest to highest temperatures
		
		ArrayList<ITemperature> highestTemp = new ArrayList<ITemperature>(10);	//A similar implementation from Task B1-1 is used here. This stores the highest temperatures
		ArrayList<String> countryNames = new ArrayList<String>();				//This will hold the country names to prevent duplicate countries
		
		int count=0;															//This represents the count of how many objects are added to the highest temperature ArrayList
		int i=dataFromDoc.size()-1;												//This is the index
		while(i>0&& count<10) {													//The loop will continue to loop if i is greater than 0 and if count is less than 10
			ITemperature current = dataFromDoc.get(i);							//This gets the current object value
			if(!(countryNames.contains(current.getCountry()))) {				//This checks to see if the country name of the current value is not in the country name ArrayList
				highestTemp.add(current);										//If that is true, the current is added to the highest temperature ArrayList
				countryNames.add(current.getCountry());							//The country name is also added to the country names ArrayList
				count++;														//count is incremented
			}
			i--;																//i is decremented
		}
		Collections.sort(highestTemp);											//The ArrayList is sorted so that it is in order from lowest to highest temperatures
		
		return highestTemp;														//An ArrayList with the top ten highest temperatures with unique countries is returned
	}

	
	//This gets all data between the range
	//Task B3
	public ArrayList<ITemperature> allCountriesGetAllDataWithinTempRange(double lowRangeTemp, double highRangeTemp) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);		//This gets input data
				
		ArrayList<ITemperature> withinRange = new ArrayList<ITemperature>();
		
		Collections.sort(dataFromDoc);											//input data is sorted
		
		for(ITemperature currentTemp : dataFromDoc ) {							//loops through every element in the ArrayList
			if(currentTemp.getTemperature(false)>=lowRangeTemp && 				//This checks if the temperature is between the bounds
					currentTemp.getTemperature(false)<=highRangeTemp) {
				withinRange.add(currentTemp);									//If that is true, it is added to the withinRange ArrayList
			}
		}

		return withinRange;														//An ArrayList with all objects with a temperature between the range is returned
	}


	//This gets the countries with the greatest temperature delta given month, and two years
	//Task C1
	public ArrayList<ITemperature> allCountriesTop10TempDelta(int month, int year1, int year2) {
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);						//This gets the data from the file
		String wordMonth = getMonthInWords(month);												//This gets the months in words
		
		ArrayList<ITemperature> sort = new ArrayList<ITemperature>();
		
		for(ITemperature value: dataFromDoc) {
			if(value.getMonth().equals(wordMonth)) {
				if(value.getYear()==year1 || value.getYear()==year2) {
					sort.add(value);															//values with the appropriate month and years are added to the ArrayList
				}
			}
		}
		ArrayList<String> countries = new ArrayList<String>();									//This will store country names to prevent repeat countries
		HashMap<Double, ITemperature> differences = new HashMap<Double, ITemperature>();		//This will map the object and temperature difference 
		for(int i=0; i<sort.size(); i++) {
			String current = sort.get(i).getCountry();											//This stores the country of the current object
			if(!countries.contains(current)) {													//This checks of it is not already in the ArrayList. This prevents duplicate countries
				countries.add(current);															//If it is true, it is added
				for(int j = i+1; j<sort.size(); j++) {											//This is another loop to trace through sort
					String second = sort.get(j).getCountry();									//This gets the country of the current object
					if(current.equals(second)) {												//This compares the countries of the two objects
						double currentTemp = sort.get(i).getTemperature(false);					//this stores the temperature of the first object
						double secondTemp = sort.get(j).getTemperature(false);					//this stores the temperature of the second object
						double absoluteValue = Math.abs(currentTemp - secondTemp);				//This is the absolute value of their difference
						differences.put(absoluteValue, sort.get(i));							//The object is put into the HashMap along with the difference
					}
				}
			}
		}
		ArrayList<ITemperature> topTen = new ArrayList<ITemperature>();
		TreeMap<Double, ITemperature> sorted = new TreeMap<Double, ITemperature>(differences);	//Putting the HashMap into a TreeMap allows for the map to be ordered
		int count=sorted.size()-10;																
		int i= 0; 																				//current value
		for(Double d: sorted.keySet()) {														//This traces through the map
			if(i>=count) {																		//This checks if i is going through the last ten objects in the map
				topTen.add(sorted.get(d));														//The ITemperature object is added to the topTen ArrayList
			}
			i++;																				//i is incremented
		}
		return topTen;																			//return the ArrayList of the top ten greatest temperature differences
	}
	
	
	//This returns a list of all unique countries in the data file
	//helper method for runClimateAnalyzer()
	public ArrayList<String> getValidCountries(){
		WeatherIO obj = new WeatherIO();
		ArrayList<ITemperature> dataFromDoc = obj.readDataFromFile(file);	//gets data from the file
		ArrayList<String> c = new ArrayList<String>();
		
		for(ITemperature t: dataFromDoc) {									//traces through all objects from the data file
			if(!c.contains(t.getCountry())) {								//this checks if the country is not already in the file
				c.add(t.getCountry());										//it is added if it is not already in the file
			}
		}
		return c;															//the ArrayList of countries is returned
	}
	
	
	//This returns an ArrayList from 1-12 of valid months
	//helper method for runClimateAnalyzer()
	public ArrayList<Integer> getValidMonth() {
		ArrayList<Integer> m = new ArrayList<Integer>();
		for(int i=1; i<=12; i++) {
			m.add(i);										//adds values 1-12 to the ArrayList
		}
		return m;											//ArrayList of valid months is returned
	}
	
	
	//helps ensure that an error is not thrown when a user inputs a wrong value and also checks if the month is valid
	//helper method for runClimateAnalyzer()
	public Boolean check(String m) {
		try {
			m.trim();
			int value = Integer.parseInt(m);				//input value is converted to a String
			ArrayList<Integer> month = getValidMonth();		//getValidMonth() is stored in this ArrayList 
			if(month.contains(value)) {						//this checks to see if the month is valid
				return true;								//If yes, true is returned
			}
			return false;									//if not, false is returned
		}
		catch(Exception f) {
			return false;									//If user inputs a value that cannot be parsed into an int, this prevents an error from being thrown
		}
	}
	
	
	//This returns an ArrayList of valid years
	//helper method for runClimateAnalyzer()
	public ArrayList<Integer> getValidYear(){
		ArrayList<Integer> y = new ArrayList<Integer>();
		int value = 0;
		for(int i=0; i<17; i++) {							//this traces through 0-12
			value = 2000 + i;								
			y.add(value);									//values between 2000-2016 are added
		}
		return y;											//ArrayList of valid years is returned
	}
	
	
	//This helps ensure that an error is not thrown when a user inputs a wrong value and also checks if the year is valid
	//helper method for runClimateAnalyzer()
	public Boolean checkY(String y) {
		try {
			y.trim();
			int value = Integer.parseInt(y);				//input value is converted to an int
			ArrayList<Integer> year = getValidYear();		//getValidYear() is stored in an ArrayList
			if(year.contains(value)) {						//this checks if the value is in the ArrayList
				return true;								//If yes, true is returned
			}
			return false;									//If not, false is returned
		}
		catch(Exception e) {
			return false;									//If user inputs a value that cannot be parsed into an int, this prevents an error from being thrown
		}
	}
	
	
	//This checks to see if the temperature is valid and helps ensure that an error is not thrown when a user inputs an incorrect value
	//helper method for runClimateAnalyzer()
	public Boolean tempCheck(String t, String t1) {
		try {
			t.trim();
			t1.trim();
			double value = Double.parseDouble(t);			//converts first String to double
			double value2 = Double.parseDouble(t1);			//converts second String to double
			if(value<=value2) {								//checks that the first value is less than or equal to the second
				return true;								//If yes, true is returned
			}
			return false;									//If not, false is returned
		}
		catch(Exception e) {
			return false;									//If user inputs a value that cannot be parsed into an int, this prevents an error from being thrown
		}
	}
	
	
	//This runs the whole program
	public void runClimateAnalyzer() {
		Scanner in = new Scanner(System.in);											//creates a Scanner for user input
		WeatherIO w = new WeatherIO();
		
		ArrayList<String> validCountries = getValidCountries();							//gets the valid countries
		
		String heading = "Temperature, Year, Month_Avg, Country, Country_Code";			//initializes the heading
		
		try {
			//This segment of code runs Task A1
			String current = "data/taskA1_climate_info.csv";							//String for the file
			System.out.println("Task A1: Get Highest and Lowest Temperature by Country and Month");
			w.writeSubjectHeaderInFile(current, "Task A1: Get Lowest and Highest Temperature by Country and Month"); //This writes the header for A1 in the file
				
			boolean found = false;
			while(!found) {														
				System.out.println("Enter a Country name");						
				String c1 = in.nextLine();												//Prompts the user for the country
			
				if(validCountries.contains(c1)) {										//Checks if the country is valid
					System.out.println("Enter the Month in numbers");					//If it is, it prompts the user for month
					String m1 = in.nextLine();

					if(check(m1)) {														//This checks to see if the month is valid
						m1.trim();														//it is trimmed
						int m = Integer.parseInt(m1);									//it is converted to an int
						w.writeSubjectHeaderInFile(current, "Task A1: Lowest Temperature of " + c1 + " in " + getMonthInWords(m));	
						ArrayList<ITemperature> store = new ArrayList<ITemperature>();
						store.add(getLowestTempByMonth(c1,m));							//because the writeDataToFile method takes in a ArrayList it is placed in one
						w.writeDataToFile(current, heading, store);						//writes data in file
					
						w.writeSubjectHeaderInFile(current, "Task A1: Highest Temperature of " + c1 + " in " + getMonthInWords(m));
						ArrayList<ITemperature> store1 = new ArrayList<ITemperature>();
						store1.add(getHighestTempByMonth(c1,m));						//because the writeDataToFile method takes in a ArrayList it is placed in one
						w.writeDataToFile(current, heading, store1);					//writes data in file
						found = true;													//sets found = true to exit while loop
						}
					else {
						System.out.println("Wrong inputs for month. Please enter the values again. Enter a number"); //If month is not valid, it prints and repromts
					}
				}
				else {
					System.out.println("Wrong inputs for country. Please enter the values again. Check spelling and capitalization"); //If country is not valid, it prints and reprompts
				}
			}
			//This is the end of A1
			//This segment of code runs A2
			current = "data/taskA2_climate_info.csv";									//changes the string of the file
			System.out.println("Task A2: Get Highest and Lowest Temperature by Country and Year");
			w.writeSubjectHeaderInFile(current, "Task A2: Get Lowest and Highest Temperature by Country and Year");
				
			boolean found1 = false;
			while(!found1) {
				System.out.println("Enter a Country name");
				String c1 = in.nextLine();												//prompts for country
				
				if(validCountries.contains(c1)) {										//checks if country is valid
					System.out.println("Enter the Year in numbers");
					String y1 = in.nextLine();											//prompts for year
			
					if(checkY(y1)){														//checks if year is valid
						
						y1.trim();														//trims year
						int y = Integer.parseInt(y1);									//converts year into int
					
						w.writeSubjectHeaderInFile(current, "Task A2: Lowest Temperature of " + c1 + " in " + y);
						ArrayList<ITemperature> store = new ArrayList<ITemperature>();
						store.add(getLowestTempByYear(c1,y));							//because the writeDataToFile method takes in a ArrayList it is placed in one
						w.writeDataToFile(current, heading, store);						//writes data in file
					
						w.writeSubjectHeaderInFile(current, "Task A2: Highest Temperature of " + c1 + " in " + y);
						ArrayList<ITemperature> store1 = new ArrayList<ITemperature>();
						store1.add(getHighestTempByYear(c1,y));							//because the writeDataToFile method takes in a ArrayList it is placed in one
						w.writeDataToFile(current, heading, store1);					//writes data in file
						found1 = true;													//sets found1 = true to exit while loop
					
					}
					else {
						System.out.println("Wrong inputs for the year. Please enter the values again. Enter a number");	//if month is not valid, prints this and reprompts
					}
				}
				else {
					System.out.println("Wrong inputs for country. Please enter the values again. Check spelling and capitalization");	//if country is not valid, prints this and reprompts
				}
			}
			//This is the end of A2
			//This segment of code runs A3
			current = "data/taskA3_climate_info.csv";									//changes file
			System.out.println("Task A3: Get All data within the Temperature Range");
			
			boolean found2 = false;
			while(!found2) {
				System.out.println("Enter a Country name");
				String c1 = in.nextLine();												//prompts for country
				if(validCountries.contains(c1)) {										//checks if country is valid
					System.out.println("Enter the lower bound for Temperature");
					String t1 = in.nextLine();											//prompts for temperature
				
					System.out.println("Enter the upper bound for Temperature");
					String t2 = in.nextLine();											//prompts for temperature
			
					if(tempCheck(t1,t2)) {												//check if both temperature values are valid
						t1.trim();														//both values are trimmed
						t2.trim();
						double v1 = Double.parseDouble(t1);								//both values are converted into doubles
						double v2 = Double.parseDouble(t2);
						
						if(getTempWithinRange(c1, v1, v2).size()>0) {					//this checks if there are any values in the range
							ArrayList<ITemperature> store = new ArrayList<ITemperature>(getTempWithinRange(c1, v1, v2));
							w.writeSubjectHeaderInFile(current, "Task A3: Get All Temperature Readings for " + c1 + " Between " + v1 + " and "+ v2);
							w.writeDataToFile(current, heading, store);					//writes data in file
							found2 = true;												//sets found2 = true to exit while loop
						}
						else {
							System.out.println("There are no values between this range. Please enter the values again.");	//if there are no values between the range, this is printed and reprompts for inputs
						}
					}
					else {
						System.out.println("Wrong inputs for temperature. Please enter the values again. Be sure to enter a number value"); //if month is not valid, prints and reprompts
					}
				}
				else {
					System.out.println("Wrong inputs for country. Please enter the values again. Check spelling and capitalization"); //if country is not valid, prints and repromts
				}
			}
			//This is the end of A3
			//This segment of code runs A4
			current = "data/taskA4_climate_info.csv";									//changes file
			System.out.println("Task A4: Get Highest and Lowest Temperature by Country");
			w.writeSubjectHeaderInFile(current, "Task A4: Get Lowest and Highest Temperature by Country");
				
			boolean found3 = false;
			while(!found3) {
				System.out.println("Enter a Country name");								//prompts for country
				String c1 = in.nextLine();
			
				if(validCountries.contains(c1)) {										//checks if country is valid
					
					w.writeSubjectHeaderInFile(current, "Task A4: Lowest Temperature for " + c1);
					ArrayList<ITemperature> store = new ArrayList<ITemperature>();
					store.add(getLowestTempYearByCountry(c1));							//because the writeDataToFile method takes in a ArrayList it is placed in one
					w.writeDataToFile(current, heading, store);							//writes data to file
					
					w.writeSubjectHeaderInFile(current, "Task A4: Highest Temperature for " + c1);
					ArrayList<ITemperature> store1 = new ArrayList<ITemperature>();
					store1.add(getHighestTempYearByCountry(c1));						//because the writeDataToFile method takes in a ArrayList it is placed in one
					w.writeDataToFile(current, heading, store1);						//writes data to file
					found3 = true;														//sets found3 = true to exit out of the while loop
				}
				else {
					System.out.println("Wrong input for country. Please enter the values again. Check spelling and capitalization"); //If country values are not valid, prints and prompts again
				}
			}
			//This is the end of A4
			//This block of code runs B1
			current = "data/taskB1_climate_info.csv";									//changes file
			w.writeSubjectHeaderInFile(current, "Task B1: Get Top Ten Lowest and Highest Temperatures by Month");
			System.out.println("Task B1: Get Top Ten Lowest and Highest Temperatures by Month");
		
			boolean found4 = false;
			while(!found4) {
				System.out.println("Enter a Month in numbers");
				String m1 = in.nextLine();												//prompts for month
		
				if(check(m1)) {															//check if month is valid
				
					m1.trim();															//trims month
					int m = Integer.parseInt(m1);										//converts to int
					w.writeSubjectHeaderInFile(current, "Task B1: Top Ten Lowest Temperatures for " + getMonthInWords(m));
					w.writeDataToFile(current, heading, allCountriesGetTop10LowestTemp(m));	//writes data to file
		
					w.writeSubjectHeaderInFile(current, "Task B1: Top Ten Highest Temperatures for " + getMonthInWords(m));
					w.writeDataToFile(current, heading, allCountriesGetTop10HighestTemp(m)); //writes data to file
					found4 = true;														//sets found 4 = true to exit out of the while loop
					}	
				else {
					System.out.println("Wrong input for month. Please enter the values again. Please enter a number value");
				}
			}
			//This is the end of B1
			//This runs B2
			current = "data/taskB2_climate_info.csv"; 									//changes file
			w.writeSubjectHeaderInFile(current, "Task B2: Get Top Ten Lowest and Highest Temperatures");
			//B2 does not require any prompting
			
			w.writeSubjectHeaderInFile(current, "Task B2: Top Ten Lowest Temperatures");
			w.writeDataToFile(current, heading, allCountriesGetTop10LowestTemp());		//writes data to file
				
			w.writeSubjectHeaderInFile(current, "Task B2: Top Ten Highest Temperatures");
			w.writeDataToFile(current, heading, allCountriesGetTop10HighestTemp());		//writes data to file
			//This is the end of B2
			//This segment of code represents B3
			System.out.println("Task B3: Get all data between two temperature bounds");
			current = "data/taskB3_climate_info.csv";									//changes file
			
			boolean found5 = false;
			while(!found5) {
				System.out.println("Enter a value for the lower bound for temperature");
				String t1 = in.nextLine();												//prompts for temperature
			
				System.out.println("Enter a value for the upper bound for temperature");
				String t2 = in.nextLine();												//prompts for temperature
		
				if(tempCheck(t1,t2)) {													//checks to see if temperature is valid
					t1.trim();
					t2.trim();															//trims both temperature values
					
					double v = Double.parseDouble(t1);
					double v2 = Double.parseDouble(t2);									//converts both values to doubles
					
					if(allCountriesGetAllDataWithinTempRange(v,v2).size()>0) {			//this checks to see if there are values in the ArrayList
						w.writeSubjectHeaderInFile(current, "Task B3: Get All Temperature Readings between " + v + " and " + v2);
						w.writeDataToFile(current, heading, allCountriesGetAllDataWithinTempRange(v,v2)); //writes data to file
						found5 = true;														//sets found 5= true to exit the while loop
					}
					else {
						System.out.println("There are no values between this range. Please enter the values again.");	//if there are no values between the range, this is printed and reprompts for inputs
					}
				}
				else {
					System.out.println("Wrong input for temperatures. Please enter the values again. Please enter a number value"); //if temperatures are not valid, prints and reprompts
				}
			}
			//This is the end of B3
			//This segment of code runs C1	
			System.out.println("Task C: Get all countries with the largest temperature difference");
			current = "data/taskC1_climate_info.csv";									//changes file
			heading = "Temperature Delta, Year Delta, Month, Country, Country_Code";	//changes header
			
			boolean found6 = false;
			while(!found6) {
				System.out.println("Enter a month in numbers");
				String m1 = in.nextLine();												//prompts for month	
			
				if(check(m1)) {															//checks if month is valid
					m1.trim();															//trims month
					int v = Integer.parseInt(m1);										//converts to int
					
					System.out.println("Enter a value for the first year");
					String y1 = in.nextLine();											//prompts for year
			
					System.out.println("Enter a value for the second year");
					String y2 = in.nextLine();											//prompts for second year
		
					if(checkY(y1) && checkY(y2)) {										//checks to see if the years are valid
						
						y1.trim();
						y2.trim();														//trim the years
						int year1 = Integer.parseInt(y1);
						int year2 = Integer.parseInt(y2);								//converts the years into ints
						
						w.writeSubjectHeaderInFile(current, "Task C1: Get Top Ten Greatest Temperature Differences for the Month of " + getMonthInWords(v) + " Between " + year1 + " and " + year2);
						w.writeDataPartC(current, heading, allCountriesTop10TempDelta(v, year1, year2), w.readDataFromFile(file), year2); //write to file
						found6 = true;													//set found6 = true to exit while loop
					}
					else {
						System.out.println("Wrong input for years. Please enter the values again. Please enter a number");	//If years are not valid, prints and repromts
					}
				}
				else {
					System.out.println("Wrong input for month. Please enter the values again. Please enter a number"); //If months are not valid, prints and reprompts.\
				}
			}	
		}
		catch(IOException e) {
			e.printStackTrace();
		}		
	}
	public static void main(String[] args) {
		ClimateAnalyzer n = new ClimateAnalyzer("data/world_temp_2000-2016.csv");
		n.runClimateAnalyzer();
	}
}
