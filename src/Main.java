
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        boolean player1 = true;
        int[] points = new int[2];
        int points2 = 0;
        boolean[][] busy1 = new boolean[12][12];
        char[][] poleBitwy1 = new char[12][12];
        char[][] fog1 = new char[12][12];
        boolean[][] busy2 = new boolean[12][12];
        char[][] poleBitwy2 = new char[12][12];
        char[][] fog2 = new char[12][12];
        int[] tab = new int[4];
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                poleBitwy1[i][j] = '~';
                fog1[i][j] = '~';
                poleBitwy2[i][j] = '~';
                fog2[i][j] = '~';
            }
        }
        System.out.println("Player 1, place your ships on the game field");
        drukowanie(poleBitwy1);
        wprowadzanie(tab, busy1, 5, poleBitwy1, "Aircraft Carrier");
        wprowadzanie(tab, busy1, 4, poleBitwy1, "Battleship");
        wprowadzanie(tab, busy1, 3, poleBitwy1, "Submarine ");
        wprowadzanie(tab, busy1, 3, poleBitwy1, "Cruiser");
        wprowadzanie(tab, busy1, 2, poleBitwy1, "Destroyer");
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        drukowanie(poleBitwy2);
        wprowadzanie(tab, busy2, 5, poleBitwy2, "Aircraft Carrier");
        wprowadzanie(tab, busy2, 4, poleBitwy2, "Battleship");
        wprowadzanie(tab, busy2, 3, poleBitwy2, "Submarine ");
        wprowadzanie(tab, busy2, 3, poleBitwy2, "Cruiser");
        wprowadzanie(tab, busy2, 2, poleBitwy2, "Destroyer");
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();

        do {
            if(n%2 == 0) {
                drukowanieBitwy(fog2, poleBitwy1);
                System.out.println("Player1, it's your turn:");
                takeAShot(poleBitwy2, fog2, points, true);
            }else{
                drukowanieBitwy(fog1, poleBitwy2);
                System.out.println("Player2, it's your turn:");
                takeAShot(poleBitwy1, fog1, points, false);
            }
            n++;
        }while(points[0] < 5 && points[1] < 5);
        if(points[0] == 5) System.out.println("You sank the last ship. You won. Congratulations!");
        if(points[1] == 5) System.out.println("You sank the last ship. You won. Congratulations!");


    }

    public static void wprowadzanie(int[] tab, boolean[][] busy, int size, char[][] poleBitwy, String name){
        boolean flag = false;
        int temp;
        do {
            do {
                System.out.println();
                System.out.printf(("Enter the coordinates of the %s (%d cells):\n"), name, size);
                tab = wpisz();
                if (tab[0] < 0 || tab[0] > 10 || tab[1] < 0 || tab[1] > 10 ||
                        tab[2] < 0 || tab[2] > 10 || tab[3] < 0 || tab[3] > 10) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                }
            } while (tab[0] < 0 || tab[0] > 10 || tab[1] < 0 || tab[1] > 10 ||
                    tab[2] < 0 || tab[2] > 10 || tab[3] < 0 || tab[3] > 10);

            if(tab[0] > tab[2]){         //  zamiana koniec z poczatkiem statku
                 temp = tab[0];
                 tab[0] = tab[2];
                 tab[2] = temp;
            }
            if(tab[1] > tab[3]){         //  zamiana koniec z poczatkiem statku
                temp = tab[1];
                tab[1] = tab[3];
                tab[3] = temp;
            }
            if (tab[0] != tab[2] && tab[1] != tab[3]) {
                System.out.println("Error! Wrong ship location! Try again:");
            } else if ((tab[0] == tab[2] && Math.abs(tab[3] - tab[1]) != size - 1)
                    || (tab[1] == tab[3] && Math.abs(tab[2] - tab[0]) != size - 1)) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n",name);
            } else if (isBusy(tab, busy, size)) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            } else flag = true;

        } while (!flag);

        if (tab[0] == tab[2]) {       // poziomy
            for (int i = 0; i < size; i++) {
                poleBitwy[tab[0]][tab[1] + i] = 'o';
                busy[tab[0]][tab[1] + i] = true;
            }
        } else if (tab[1] == tab[3]) {          //pionowy
            for (int i = 0; i < size; i++) {
                poleBitwy[tab[0] + i][tab[1]] = 'o';
                busy[tab[0] + i][tab[1]] = true;
            }
        }
        drukowanie(poleBitwy);
    }
    public static void drukowanie(char[][] poleBitwy){
        System.out.println();
        System.out.print("  1 2 3 4 5 6 7 8 9 10");
        for(int i = 1 ;i < 11 ; i++){
            System.out.println();
            System.out.printf("%c ",64 + i);
            for(int j = 1 ; j < 11 ; j++){
                System.out.printf("%c ", poleBitwy[i][j]);
            }
        }
        System.out.println();System.out.println();
    }
    public static void drukowanieBitwy(char[][] fog, char[][] poleBitwy){
        System.out.println();
        System.out.print("  1 2 3 4 5 6 7 8 9 10");
        for(int i = 1 ;i < 11 ; i++){
            System.out.println();
            System.out.printf("%c ",64 + i);
            for(int j = 1 ; j < 11 ; j++){
                System.out.printf("%c ", fog[i][j]);
            }
        }
        System.out.println();System.out.println("---------------------");
        System.out.print("  1 2 3 4 5 6 7 8 9 10");
        for(int i = 1 ;i < 11 ; i++){
            System.out.println();
            System.out.printf("%c ",64 + i);
            for(int j = 1 ; j < 11 ; j++){
                System.out.printf("%c ", poleBitwy[i][j]);
            }
        }
        System.out.println();
        System.out.println();
    }
    public static int[] wpisz(){
        int[] tab = new int[4];
        Scanner scanner = new Scanner(System.in);
        String wpis;
        do {
            System.out.println();
            wpis = scanner.nextLine();
            if(wpis.length() < 5){
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        } while(wpis.length() < 5);
        tab[0] = wpis.charAt(0) - 64;
        if(wpis.charAt(2) == ' '){
            tab[1] = wpis.charAt(1) - 48;
            tab[2] = wpis.charAt(3) - 64;
            if(wpis.length() == 5){
                tab[3] = wpis.charAt(4) - 48;
            }else if(wpis.charAt(5) == '0'  && wpis.charAt(4) == '1'){
                tab[3] = 10;
            }else tab[3] = 99;
        }else if(wpis.charAt(3) == ' ' && wpis.charAt(2) == '0' && wpis.charAt(1) == '1'){
            tab[1] = 10;
            tab[2] = wpis.charAt(4) - 64;
            if(wpis.length() == 6){
                tab[3] = wpis.charAt(5) - 48;
            }else if(wpis.charAt(6) == '0'  && wpis.charAt(5) == '1'){
                tab[3] = 10;
            }else tab[3] = 99;
        }else tab[1] = 99;

        return tab;
    }
    public static boolean isBusy(int[] tab, boolean[][] busy, int size){
        boolean isbusy = false;
            if(tab[0] == tab[2]){                  // znaczy ze jest poziomy
                for(int j = 0 ; j < 3 ; j++) {
                    for (int i = 0; i < size + 2; i++) {
                        if (busy[tab[0] + j - 1][tab[1] + i - 1]) {
                            isbusy = true;
                        }
                    }
                }
            }else if(tab[1] == tab[3]){             // znaczy ze jest pionowy
                for(int j = 0 ; j < 3 ; j++) {
                    for (int i = 0; i < size + 2; i++) {
                        if (busy[tab[0] + i - 1][tab[1] + j - 1]) {
                            isbusy = true;
                        }
                    }
                }
            }
        return isbusy;
    }
    public static int[] takeAShot(char[][] poleBitwy, char[][] fog, int[] points, boolean player1){
        boolean sank = false;
        int k;
        Scanner scanner = new Scanner(System.in);
        int[] taba = new int[2];
        String wpis;
        System.out.println();
        do {
            do {
                wpis = scanner.nextLine();
                if (wpis.length() < 2) System.out.println("Error! You entered the wrong coordinates! Try again:");
            } while (wpis.length() < 2);
            taba[0] = wpis.charAt(0) - 64;
            if (wpis.length() == 2) {
                taba[1] = wpis.charAt(1) - 48;
            } else if (wpis.length() == 3) {
                if (wpis.charAt(2) == '0' && wpis.charAt(1) == '1') {
                    taba[1] = 10;
                } else taba[1] = 99;
            } else taba[0] = 99;
            if(taba[0] < 0 || taba[0] > 10 || taba[1] < 0 || taba[1] > 10){
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        }while(taba[0] < 0 || taba[0] > 10 || taba[1] < 0 || taba[1] > 10);
        // ---------- mamy juz gotowy prawidlowy wpis zapisany w taba jako liczby------------------
        if(poleBitwy[taba[0]][taba[1]] == 'o' ){

            poleBitwy[taba[0]][taba[1]] = 'X';
            fog[taba[0]][taba[1]] = 'X';
            // ---- sprawdzamy czy statek zatopiony
            sank = true;   // zakladamy  za tak jest
            for(int i = 0 ; i < 3 ; i++){
                for(int j = 0 ; j < 3 ; j++){
                    if(poleBitwy[taba[0] + i - 1][taba[1] + j - 1] == 'o' ) {
                        sank = false;
                        break;
                    }
                }
                if(!sank) break;
            }

            drukowanie(fog);
            if(player1){ k = 0;} else k = 1;
            if(sank){
                points[k]++;
                if(points[k] < 5) System.out.println("You sank a ship!");
            }else {
                System.out.println("You hit a ship!");
            }
        }else if(poleBitwy[taba[0]][taba[1]] == 'X' ){
            drukowanie(fog);
            System.out.println("You hit a ship!");
        }else {
            poleBitwy[taba[0]][taba[1]] = 'M';
            fog[taba[0]][taba[1]] = 'M';
            System.out.println("You missed!");
        }
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        scanner.nextLine();


        return points;
    }

}