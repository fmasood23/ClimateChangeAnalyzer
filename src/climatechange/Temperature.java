package climatechange;

import java.util.HashMap;

//This class models a temperature object
public class Temperature implements ITemperature, Comparable<ITemperature>{
	
	
	private String country;				//this holds the value of the country name
	private String threeLetterCode;		//this is the three letter abbreviation of the country
	private String month;				//This represents the month of the given temperature
	private int	   year;				//This is the year of the temperature
	private double temp;				//This gives the temperature value
	
	
	//This constructs the instance variables
	public Temperature(String country, String threeLetterCode, String month, int year, double temp) {
		this.country = country;
		this.threeLetterCode = threeLetterCode;
		this.month = month;
		this.year = year;
		this.temp = temp;
	}
	
	
	//This returns the country
	public String getCountry() {
		return country;
	}
	
	
	//This returns the three letter code
	public String getCountry3LetterCode() {
		return threeLetterCode;
	}
	
	
	//This returns the month in a String
	public String getMonth() {
		return month;
	}
	
	
	//This returns the year
	public int getYear() {
		return year;
	}
	
	
	//This gets the temperature
	public double getTemperature(boolean getFahrenheit) {
		if(getFahrenheit==false) {	//if the condition is false, the temperature is returned in Celcius
			return temp;
		}
		return temp*(9.0/5.0)+32;	//if the condition is true, the temperature is returned in Farenheit
	}

	
	//This method compares two objects by temperature, country name, year, and month.
	public int compareTo(ITemperature o) {
		Temperature t = (Temperature)o;
		int value0 = Double.compare(this.getTemperature(false), o.getTemperature(false));	//This compares the temperature
		if(value0!=0) {
			return value0;																	//If the temperatures are not equal, a value is returned and no more checks are needed
		}
		else {
			int value = this.getCountry().compareTo(t.getCountry());						//if the temperature is the same, it proceeds to compare countries
			if(value!=0) {												
			return value;																	//If the countries are not equal, a value is returned and no more checks are needed
			}
			else{
				int value2 = Integer.compare(this.getYear(), t.getYear());					//if the countries are equal, it proceeds to compare the years
				if(value2!=0) {
					return value2;															//if the years are not the same, a value is returned and no more checks are needed
				}
				else {
					int thisOne = this.getMonthInNumbers(getMonth());						//I called a method I wrote to convert the String month to an integer value
					int other = t.getMonthInNumbers(t.getMonth()); 							//If the years are equal, it proceeds to compare months
					return Integer.compare(thisOne, other);									//this last check compares and returns the months
				}
			}
		}
	}
	
	
	//A compareTo method needs a compatible equals method that compares two objects
	public boolean equals(Object x){
		Temperature t =(Temperature)x;
		return this.compareTo(t)==0;	//checks to see if two objects are equal
	}
	
	
	//An equals method needs a hashcode method
	public int hashCode() {
		return getYear() + country.hashCode();	//I created my own hashcode through manipulating the variables
	}
	
	
	//This method converts the String month to an integer. This method is used in the compareTo method.
	public int getMonthInNumbers(String month) {
		int months =0;
		
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		String[] words = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
		for(int i=0; i<12; i++) {				//I looped through 12 places to put 12 keys and values into data
			data.put(words[i], i+1);			//Now the months in Strings are associated with the integer values
		}
		for(String value: data.keySet()) {		//this goes through the hashMap
			if(value.equals(month)) {			//this checks to see if the current String is equal to the String month method parameter
				months = data.get(value);		//if that is true, I am storing the integer value in a variable
			}
		}
		return months;							//the variable with the integer value is returned.
	}
}
