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

    public static Scanner scanner = new Scanner(System.in);
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

    public static boolean isCloseTo(int x, int y) {
        // Controllo per la casella in alto (x-1)
        if (x - 1 >= 0) {
            if (matrix[x - 1][y] != turno && matrix[x - 1][y] != 0) {
                return true;
            }
        }

        // Controllo per la casella in basso (x+1)
        if (x + 1 < 8) {
            if (matrix[x + 1][y] != turno && matrix[x + 1][y] != 0) {
                return true;
            }
        }

        // Controllo per la casella a sinistra (y-1)
        if (y - 1 >= 0) {
            if (matrix[x][y - 1] != turno && matrix[x][y - 1] != 0) {
                return true;
            }
        }

        // Controllo per la casella a destra (y+1)
        if (y + 1 < 8) {
            if (matrix[x][y + 1] != turno && matrix[x][y + 1] != 0) {
                return true;
            }
        }

        // Controllo per la diagonale in basso a destra (x+1, y+1)
        if (x + 1 < 8 && y + 1 < 8) {
            if (matrix[x + 1][y + 1] != turno && matrix[x + 1][y + 1] != 0) {
                return true;
            }
        }

        // Controllo per la diagonale in basso a sinistra (x+1, y-1)
        if (x + 1 < 8 && y - 1 >= 0) {
            if (matrix[x + 1][y - 1] != turno && matrix[x + 1][y - 1] != 0) {
                return true;
            }
        }

        // Controllo per la diagonale in alto a destra (x-1, y+1)
        if (x - 1 >= 0 && y + 1 < 8) {
            if (matrix[x - 1][y + 1] != turno && matrix[x - 1][y + 1] != 0) {
                return true;
            }
        }

        // Controllo per la diagonale in alto a sinistra (x-1, y-1)
        if (x - 1 >= 0 && y - 1 >= 0) {
            if (matrix[x - 1][y - 1] != turno && matrix[x - 1][y - 1] != 0) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean controllo(int x, int y) {
        if(matrix[x][y]!=0) {
            return false;
        }
        if(x<0||x>7||y<0||y>7) {
            return false;
        }
        
        if(!isCloseTo(x, y)){
        	return false;
        }
        
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
                y=askCoordinate();
                xy= 'y';
                
                x=askCoordinate();
                xy= 'x';
                if(controllo(x, y)==true) {
                    break;
                }else{
                	System.out.println("opzione selezionata non valida");
                }
            }
            matrix[x][y]=turno;
            if(turno==2){
            	turno=1;
            }else{
            	turno=2;
            }
        }
        scanner.close(); //  chiudi solo alla fine
    }
}
