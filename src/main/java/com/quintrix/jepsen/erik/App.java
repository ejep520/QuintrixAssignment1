package com.quintrix.jepsen.erik;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * Erik Jepsen<br>
 * Assignment 1<br>
 * Quintrix preemptive Java, Angular &amp; AWS Fall 2022<br>
 * 2022-08-12
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	List<Human> classmates;
        Set<String> emails;
        Map<Human, Integer> gradeBook;
        Path inFile;
        ReportWriter writer;
        final String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern regexPattern;
        classmates = new ArrayList<>();
        emails = new HashSet<>();
        gradeBook = new HashMap<>();
        regexPattern = Pattern.compile(pattern);
        inFile = Paths.get("C:", "Users", "erik", "grades.csv");
        writer = new ReportWriter();
        try (CSVParser csvParser = CSVParser.parse((Reader)(new FileReader(inFile.toString())), CSVFormat.EXCEL)) {
        	csvParser.forEach(thisLine -> {
        		Human newGuy;
        		if (!thisLine.get(0).equals("name")) {
        			if (regexPattern.matcher(thisLine.get(1)).matches()) {
        				if (!emails.add(thisLine.get(1))) {
        					System.err.printf("%s's email is not unique!", thisLine.get(0));
        				}
        			} else {
        				System.err.printf("%s's email address is not valid.\n", thisLine.get(0));
        			}
        			newGuy = new Human(thisLine.get(0));
        			newGuy.SetEmail(thisLine.get(1));
        			classmates.add(newGuy);
        			gradeBook.put(newGuy, Integer.parseInt(thisLine.get(2)));
        		}
        	});
        }
        catch (FileNotFoundException e) {
        	System.err.println("Could not find the grades!");
        	e.printStackTrace();
        	return;
        }
    	catch (IOException e) {
    		e.printStackTrace();
    		System.exit(1);
    	}
        System.out.println("Here is everyone in the class...");
        for (Human thisPerson: classmates) {
        	System.out.printf("%s <%s>\n", thisPerson.GetName(), thisPerson.GetEmail());
        }
        System.out.println();
        System.out.println("We keep a separate list of just the email addresses to catch duplicates. Here is that list...");
        System.out.println(emails);
        writer.CreateReport();
        System.out.println();
        System.out.printf("Writing the grades to %s\n", writer.getOutputLoc());
    	try {
    		for (Map.Entry<Human, Integer> entry: gradeBook.entrySet()) {
    			writer.WriteLine(String.format("%s -- %d%%\n", entry.getKey().GetName(), entry.getValue()));
        	}
        	writer.CloseReport();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		System.exit(1);
    	}
        System.out.println("Successful conclusion.");
    }
}
