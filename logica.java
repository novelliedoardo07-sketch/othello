package othello;

import java.util.Scanner;

public class logica { 

    public static int turno = 2;   // 1 = nero, 2 = bianco
    public static char xy = 'x';

    // matrix[y][x]
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

    // ==========================================================
    // INPUT SICURO
    // ==========================================================

    public static int askCoordinate() {
        System.out.print("Giocatore " + turno + " inserisci " + xy + ": ");

        if (!scanner.hasNextInt()) {
            scanner.next(); // scarta input non valido
            return -1;
        }

        return scanner.nextInt();
    }

    // ==========================================================
    // DIREZIONI
    // ==========================================================

    public static boolean sinistra(int x, int y) {
        if (x <= 0) return false;
        int j = x - 1;
        if (matrix[y][j] == 0 || matrix[y][j] == turno) return false;
        for (; j >= 0; j--) {
            if (matrix[y][j] == 0) return false;
            if (matrix[y][j] == turno) break;
        }
        if (j < 0) return false;
        for (int k = x - 1; k > j; k--) matrix[y][k] = turno;
        matrix[y][x] = turno;
        return true;
    }

    public static boolean destra(int x, int y) {
        if (x >= 7) return false;
        int j = x + 1;
        if (matrix[y][j] == 0 || matrix[y][j] == turno) return false;
        for (; j <= 7; j++) {
            if (matrix[y][j] == 0) return false;
            if (matrix[y][j] == turno) break;
        }
        if (j > 7) return false;
        for (int k = x + 1; k < j; k++) matrix[y][k] = turno;
        matrix[y][x] = turno;
        return true;
    }

    public static boolean su(int x, int y) {
        if (y <= 0) return false;
        int i = y - 1;
        if (matrix[i][x] == 0 || matrix[i][x] == turno) return false;
        for (; i >= 0; i--) {
            if (matrix[i][x] == 0) return false;
            if (matrix[i][x] == turno) break;
        }
        if (i < 0) return false;
        for (int k = y - 1; k > i; k--) matrix[k][x] = turno;
        matrix[y][x] = turno;
        return true;
    }

    public static boolean giu(int x, int y) {
        if (y >= 7) return false;
        int i = y + 1;
        if (matrix[i][x] == 0 || matrix[i][x] == turno) return false;
        for (; i <= 7; i++) {
            if (matrix[i][x] == 0) return false;
            if (matrix[i][x] == turno) break;
        }
        if (i > 7) return false;
        for (int k = y + 1; k < i; k++) matrix[k][x] = turno;
        matrix[y][x] = turno;
        return true;
    }

    public static boolean diagSuSinistra(int x, int y) {
        if (x <= 0 || y <= 0) return false;
        int i = y - 1, j = x - 1;
        if (matrix[i][j] == 0 || matrix[i][j] == turno) return false;
        while (i >= 0 && j >= 0) {
            if (matrix[i][j] == 0) return false;
            if (matrix[i][j] == turno) break;
            i--; j--;
        }
        if (i < 0 || j < 0) return false;
        int a = y - 1, b = x - 1;
        while (a > i && b > j) {
            matrix[a][b] = turno;
            a--; b--;
        }
        matrix[y][x] = turno;
        return true;
    }

    public static boolean diagSuDestra(int x, int y) {
        if (x >= 7 || y <= 0) return false;
        int i = y - 1, j = x + 1;
        if (matrix[i][j] == 0 || matrix[i][j] == turno) return false;
        while (i >= 0 && j <= 7) {
            if (matrix[i][j] == 0) return false;
            if (matrix[i][j] == turno) break;
            i--; j++;
        }
        if (i < 0 || j > 7) return false;
        int a = y - 1, b = x + 1;
        while (a > i && b < j) {
            matrix[a][b] = turno;
            a--; b++;
        }
        matrix[y][x] = turno;
        return true;
    }

    public static boolean diagGiuSinistra(int x, int y) {
        if (x <= 0 || y >= 7) return false;
        int i = y + 1, j = x - 1;
        if (matrix[i][j] == 0 || matrix[i][j] == turno) return false;
        while (i <= 7 && j >= 0) {
            if (matrix[i][j] == 0) return false;
            if (matrix[i][j] == turno) break;
            i++; j--;
        }
        if (i > 7 || j < 0) return false;
        int a = y + 1, b = x - 1;
        while (a < i && b > j) {
            matrix[a][b] = turno;
            a++; b--;
        }
        matrix[y][x] = turno;
        return true;
    }

    public static boolean diagGiuDestra(int x, int y) {
        if (x >= 7 || y >= 7) return false;
        int i = y + 1, j = x + 1;
        if (matrix[i][j] == 0 || matrix[i][j] == turno) return false;
        while (i <= 7 && j <= 7) {
            if (matrix[i][j] == 0) return false;
            if (matrix[i][j] == turno) break;
            i++; j++;
        }
        if (i > 7 || j > 7) return false;
        int a = y + 1, b = x + 1;
        while (a < i && b < j) {
            matrix[a][b] = turno;
            a++; b++;
        }
        matrix[y][x] = turno;
        return true;
    }

    // ==========================================================
    // APPLICA MOSSA
    // ==========================================================

    public static boolean applicaMossa(int x, int y) {
        boolean ok = false;
        ok |= sinistra(x, y);
        ok |= destra(x, y);
        ok |= su(x, y);
        ok |= giu(x, y);
        ok |= diagSuSinistra(x, y);
        ok |= diagSuDestra(x, y);
        ok |= diagGiuSinistra(x, y);
        ok |= diagGiuDestra(x, y);
        return ok;
    }

    // ==========================================================
    // CONTROLLO
    // ==========================================================

    public static boolean controllo(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) return false;
        if (matrix[y][x] != 0) return false;

        int[][] m = copiaMatrice();

        return sinistraSim(x,y,m) || destraSim(x,y,m) ||
               suSim(x,y,m) || giuSim(x,y,m) ||
               diagSuSinistraSim(x,y,m) || diagSuDestraSim(x,y,m) ||
               diagGiuSinistraSim(x,y,m) || diagGiuDestraSim(x,y,m);
    }

    public static int[][] copiaMatrice() {
        int[][] c = new int[8][8];
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                c[i][j] = matrix[i][j];
        return c;
    }

    // ==========================================================
    // VERSIONI SIM
    // ==========================================================

    public static boolean sinistraSim(int x,int y,int[][] m){ if(x<=0)return false; int j=x-1; if(m[y][j]==0||m[y][j]==turno)return false; for(;j>=0;j--){ if(m[y][j]==0)return false; if(m[y][j]==turno)break;} return j>=0; }
    public static boolean destraSim(int x,int y,int[][] m){ if(x>=7)return false; int j=x+1; if(m[y][j]==0||m[y][j]==turno)return false; for(;j<=7;j++){ if(m[y][j]==0)return false; if(m[y][j]==turno)break;} return j<=7; }
    public static boolean suSim(int x,int y,int[][] m){ if(y<=0)return false; int i=y-1; if(m[i][x]==0||m[i][x]==turno)return false; for(;i>=0;i--){ if(m[i][x]==0)return false; if(m[i][x]==turno)break;} return i>=0; }
    public static boolean giuSim(int x,int y,int[][] m){ if(y>=7)return false; int i=y+1; if(m[i][x]==0||m[i][x]==turno)return false; for(;i<=7;i++){ if(m[i][x]==0)return false; if(m[i][x]==turno)break;} return i<=7; }
    public static boolean diagSuSinistraSim(int x,int y,int[][] m){ if(x<=0||y<=0)return false; int i=y-1,j=x-1; if(m[i][j]==0||m[i][j]==turno)return false; while(i>=0&&j>=0){ if(m[i][j]==0)return false; if(m[i][j]==turno)break; i--;j--; } return i>=0&&j>=0; }
    public static boolean diagSuDestraSim(int x,int y,int[][] m){ if(x>=7||y<=0)return false; int i=y-1,j=x+1; if(m[i][j]==0||m[i][j]==turno)return false; while(i>=0&&j<=7){ if(m[i][j]==0)return false; if(m[i][j]==turno)break; i--;j++; } return i>=0&&j<=7; }
    public static boolean diagGiuSinistraSim(int x,int y,int[][] m){ if(x<=0||y>=7)return false; int i=y+1,j=x-1; if(m[i][j]==0||m[i][j]==turno)return false; while(i<=7&&j>=0){ if(m[i][j]==0)return false; if(m[i][j]==turno)break; i++;j--; } return i<=7&&j>=0; }
    public static boolean diagGiuDestraSim(int x,int y,int[][] m){ if(x>=7||y>=7)return false; int i=y+1,j=x+1; if(m[i][j]==0||m[i][j]==turno)return false; while(i<=7&&j<=7){ if(m[i][j]==0)return false; if(m[i][j]==turno)break; i++;j++; } return i<=7&&j<=7; }

    // ==========================================================
    // FINE PARTITA
    // ==========================================================

    public static boolean haMosse(int giocatore) {
        int old = turno;
        turno = giocatore;
        for(int y=0;y<8;y++)
            for(int x=0;x<8;x++)
                if(matrix[y][x]==0 && controllo(x,y)){
                    turno = old;
                    return true;
                }
        turno = old;
        return false;
    }

    public static boolean partitaFinita() {
        return !haMosse(1) && !haMosse(2);
    }

    // ==========================================================
    // STAMPA
    // ==========================================================

    public static void stampaMatrice() {
        System.out.println("    0 1 2 3 4 5 6 7");
        System.out.println("   -----------------");
        for(int y=0;y<8;y++){
            System.out.print(y+" | ");
            for(int x=0;x<8;x++)
                System.out.print(matrix[y][x]+" ");
            System.out.println();
        }
        System.out.println();
    }

    // ==========================================================
    // MAIN
    // ==========================================================

    public static void main(String[] args) {

        int gio1=0;
        int gio2=0;
         
        while (true) {
            stampaMatrice();

            if (!haMosse(turno)) {
                System.out.println("Giocatore " + turno + " non ha mosse. Turno passato.");
                turno = (turno == 1 ? 2 : 1);

                if (partitaFinita()) {
                    System.out.println("Nessun giocatore ha più mosse. Fine partita!");
                    
                    
                    for (int i = 0; i<7; i++){
                    	for(int j = 0; j<7; j++){
                    		if( matrix [i] [j] == 1)
                    			gio1 ++;
                    		if( matrix [i] [j] == 2)
                    			gio2 ++;
                    	}
                    }
                    
                   
                    break;
                }
                continue;
            }

            int x, y;

            while (true) {
                xy = 'x';
                x = askCoordinate();
                xy = 'y';
                y = askCoordinate();

                if (x == -1 || y == -1 || !controllo(x, y)) {
                    System.out.println("Mossa non valida. Riprova.");
                    continue;
                }
                break;
            }

            applicaMossa(x, y);
            turno = (turno == 1 ? 2 : 1);
        }

        stampaMatrice();
        scanner.close();
        System.out.println("Gioco terminato!");
        if(gio1 < gio2)
        	System.out.print("il giocatore 2 ha vinto\n" );
        if(gio1 == gio2)
        	System.out.print("pareggio" + '\n' );
        if(gio1 > gio2)
        	System.out.print("il giocatore 1 ha vinto\n");
        System.out.print("giocatore 1 : " + gio1 + "\n giocatore 2 : " + gio2 + '\n' );
    }
}
