package com.boris.dechev;

import org.apache.commons.cli.*;

public class Main {

    private static Options options(){
        Option size = new Option("s", "size", true, "Size of the fractal image.");
        Option rect = new Option("r", "rect", true, "Coordinates of the complex plane.");
        Option tasks = new Option("t", "tasks", true, "Number of threads.");
        Option outputName = new Option("o", "output", true,"Name of the generated image.");
        Option quietMode = new Option("q", "quiet", true, "Run with quiet mode on.");

        Options options = new Options();
        options.addOption(size);
        options.addOption(rect);
        options.addOption(tasks);
        options.addOption(outputName);
        options.addOption(quietMode);

        return options;
    }


    private static CommandLine commandLine(Options options, String[] args){
        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);
        }
        catch (ParseException e){
            throw new IllegalArgumentException("Parsing error: " + e.getMessage());
        }
    }


    private static FractalMultithreading fractalMultithreading(CommandLine cmd){
        int width = 640, height = 480;
        if(cmd.hasOption("s")){
            String[] size = cmd.getOptionValue("s").split("x");
            width = Integer.parseInt(size[0]);
            height = Integer.parseInt(size[1]);

            if(width <= 0 || height <= 0){
                throw new IllegalArgumentException("Width and height should be positive numbers!");
            }
        }

        int numberOfThreads = 1;
        if(cmd.hasOption("t")){
            numberOfThreads = Integer.parseInt(cmd.getOptionValue("t"));

            if(numberOfThreads < 1){
                throw new IllegalArgumentException("Number of threads should be a positive number");
            }
        }

        String fileName = "zad16.png";
        if(cmd.hasOption("o")){
            fileName = cmd.getOptionValue("o");
        }

        boolean isQuiet = false;
        if(cmd.hasOption("q")){
            isQuiet = true;
        }

        return new FractalMultithreading(width, height, numberOfThreads, fileName, isQuiet);
    }


    private static void generate(String[] args){
        Options options = options();
        CommandLine cmd = commandLine(options, args);
        FractalMultithreading fractal = fractalMultithreading(cmd);

        fractal.run();
    }


    public static void main(String[] args) {
        generate(args);
    }
}
