package com.github.khushipandey1805.bobo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Bobo{
	static boolean hadError=false;
	public static void main(String args[]) throws IOException{
		if(args.length>1){
			System.out.println("Usage: bobo [script]"); //bobo stuff.bobo
			System.exit(64); //exit with error code 64 (incorrect command line usage)
		}else if(args.length==1){
			runFile(args[0]); //runFile("stuff.bobo")
		}else{
			runPrompt(); //execute line by line
		}
	}
	private static void runFile(String path) throws IOException{
		byte[] bytes=Files.readAllBytes(Paths.get(path));
		run(new String(bytes, Charset.defaultCharset()));
		if(hadError) 
			System.exit(65); //data input error
	}
	private static void runPrompt() throws IOException{
		InputStreamReader input=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(input);
		for(;;){
			System.out.print("=> ");
			String line=br.readLine();
			if(line==null) break; //ctrl+D in wsl, maybe add some command for exit later?
			run(line);
			hadError=false; //reset error flag for next line
		}
	}
	private static void run(String source){
		Scanner sc=new Scanner(source);
		List<Token> tokens=sc.scanTokens();

		//print tokens only for now
		for(Token tk: tokens){
			System.out.println(tk);
		}
		//TODO: create parser, resolver, interpreter
	}
	static void error(int line, String message){
		report(line, "", message);
	}
	private static void report(int line, String where, String message){
		System.err.println("[line "+line+"] Oopsies"+where+": "+message); //[line 3] Oopsies: unexpected character
		hadError=true;
	}
}
