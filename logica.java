package othello;

import java.util.Scanner;

public class logica { 
    public static int turno = 2;
    public static char xy = 'x';
    public static int[][] matrix = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,1,2,0,0,0},
        {0,0,0,2,1,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };

    public static Scanner scanner = new Scanner(System.in); // 👈 Scanner globale

    public static boolean isFinnished() {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(matrix[i][j]==0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean sinistra(int x, int y, int ) {
    	if(!(matrix[x][y]!=turno)&&matrix[x][y]!=0) {
    		
    	}
    }
    
    public static boolean controllo(int x, int y) {
        if(matrix[x][y]!=0) {
            return false;
        }
        if(x<0||x>7||y<0||y>7) {
            return false;
        }
        sinistra(x-1, y);
        
        return true;
    }

    public static int askCoordinate() {
        System.out.println("giocatore "+turno+" inserisci la "+xy);
        int numero = scanner.nextInt();   // usa lo scanner globale
        System.out.println("Hai inserito: " + numero);
        return numero;
    }

    public static void stampaMatrice() {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }

    public static void main(String[] args) {
        int x;
        int y;
        while(isFinnished()) {
            stampaMatrice();
            while(true) {
                x=askCoordinate();
                xy= 'y';
                y=askCoordinate();
                xy= 'x';
                if(controllo(x, y)==true) {
                	
                    break;
                }
            }
        }
        scanner.close(); // 👈 chiudi solo alla fine
    }
}
