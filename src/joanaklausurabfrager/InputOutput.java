/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joanaklausurabfrager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Cheesy
 */
public class InputOutput {

    private String csvPath;
    private BufferedReader buffr = null;
    private String line = "";
    private String csvSplitBy;
    private GUI_tester gui;
    public LinkedList<String[]> frageListe;
    public int qNr = 0;
    private int right;

    public InputOutput() {
        this("./fragen.txt");

    }

    public InputOutput(String path) {
        this(path, ";");

    }

    public InputOutput(String path, String sep) {
        csvPath = path;
        csvSplitBy = sep;
        try {
            System.out.println("Before Reader");
            buffr = new BufferedReader(new FileReader(csvPath));
            System.out.println("Reader worked");
               frageListe = new LinkedList<String[]>();

            while ((line = buffr.readLine()) != null) {
                frageListe.add(line.split(csvSplitBy));
            }
            


         
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (buffr != null) {
                try {
                    buffr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        randomize();
        gui = new GUI_tester(this);

        gui.setVisible(true);
    }

    public boolean closeStream() {
        try {
            buffr.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void randomize() {
        int tempMax;
        int refWert;
        int tempIndex;

        System.out.println("Came to randomize");
        if(frageListe.size()>= 2){
        for (int i = 0; i < frageListe.size(); i++) {
            tempMax = Integer.parseInt(frageListe.get(i)[4]);
            tempIndex = i;

            for (int j = i + 1; j < frageListe.size(); j++) {
                refWert = Integer.parseInt(frageListe.get(j)[4]);
                if (tempMax < refWert) {
                    tempIndex = j;
                    tempMax = refWert;
                }

            }
            swap(i, tempIndex);

        }
        tempIndex = 0;
        while (Integer.parseInt(frageListe.get(tempIndex)[4]) != 0) {
            tempIndex++;
        }

        for (int i = tempIndex; i < frageListe.size(); i++) {
            int tempWert = -1;

            do {
                tempWert = (int) Math.round(Math.random() * (frageListe.size() - 1));

            } while (tempWert <= tempIndex);
            swap(i, tempWert);
        }}

        for (String[] frageListe1 : frageListe) {
            System.out.println(frageListe1[0]);

        }
    }

    public void swap(int i1, int i2) {
        String[] temp = frageListe.get(i1);
        frageListe.set(i1, frageListe.get(i2));
        frageListe.set(i2, temp);

    }

    public void write() {
        try {
            File f = new File(csvPath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f.getCanonicalFile()));
            for (int i = 0; i < frageListe.size(); i++) {
                String buildStr = frageListe.get(i)[0];
                for (int j = 0; j < frageListe.get(i).length; j++) {
                    buildStr += ";" + frageListe.get(i)[j];

                }
                bw.write(buildStr);

            }
        } catch (Exception e) {
        }
    }
    
    public void sout(String s){
        System.out.println(s);
    }

    public int tossQuestion() {
        String[] temp = frageListe.get(qNr);
        
        int rightAnswer = randInt(2);
        String right = temp[1];
        String wrongA1 = temp[2];
        String wrongA2 = temp[3];
        int wrong1 = -1;
        int wrong2 = 0;
        do {
            wrong1 = randInt(2);
        } while (wrong1 == rightAnswer);
        for (int i = 0; i < 3; i++) {
            if (wrong2 == wrong1 || wrong2 == rightAnswer) {
                wrong2++;
            }else{
            	break;
            }

        }
        switch (rightAnswer) {
            case 0:
                if (wrong1 == 1) {
                    gui.setQuestion(temp[0], 
                            right, 
                            wrongA1, 
                            wrongA2);
                } else {
                    gui.setQuestion(temp[0], 
                            right, 
                            wrongA2, 
                            wrongA1);

                }

                break;
            case 1:
                if (wrong1 == 0) {
                    gui.setQuestion(temp[0], 
                            wrongA1, 
                            right, 
                            wrongA2);
                } else {
                    gui.setQuestion(temp[0], 
                            wrongA2, 
                            right, 
                            wrongA1);
                }

                break;

            case 2:
                if (wrong1 == 0) {
                    gui.setQuestion(temp[0], 
                            wrongA1, 
                            wrongA2, 
                            right);
                } else {
                    gui.setQuestion(temp[0],
                            wrongA2, 
                            wrongA1, right);
                }

                break;

            default:
                System.out.println("Something went wrong");
                break;

        }
        this.right = rightAnswer;
        return rightAnswer;
    }
    
    public int getRight(){
    	return this.right;
    }

    private int randInt(int offset) {
        return (int) Math.round(Math.random() * offset);
    }

}
