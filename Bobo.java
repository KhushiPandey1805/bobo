package com.github.khushipandey1805.bobo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Bobo{
	private static final Interpreter interpreter=new Interpreter();
	static boolean hadError=false;
	static boolean hadRuntimeError=false;
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
		if(hadRuntimeError)
			System.exit(70); //runtime error
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
		Parser parser=new Parser(tokens);
		List<Stmt> statements=parser.parse();
		if(hadError)
			return;
		interpreter.interpret(statements);
	}
	static void error(int line, String message){
		report(line, "", message);
	}
	private static void report(int line, String where, String message){
		System.err.println("[line "+line+"] Uh oh"+where+"!: "+message); //[line 3] Oopsies: unexpected character
		hadError=true;
	}
	static void error(Token token, String message){
		if(token.type==TokenType.EOF){
			report(token.line, " at end", message);
		}
		else{
			report(token.line, " at '"+token.lexeme+"'",message);
		}
	}
	static void runtimeError(RuntimeError error){
		System.err.println(error.getMessage()+"\n[line "+error.token.line+"]");
		hadRuntimeError=true;
	}
}
