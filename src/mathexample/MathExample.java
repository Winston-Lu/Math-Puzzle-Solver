package mathexample;
import java.util.ArrayList;

public class MathExample {
    public final static char[][] gridRef = new char[][]{{' ','1',' ','2',' ','8',' ','5',' ','3',' ','1',' ','1',' '},          //a = -1 | b = -2     
                                                        {'b','#','2','#','z','#','5','#','a','#','5','#','z','#','a'},          // z=*2 | y = *3
                                                        {' ','2',' ','7',' ','a',' ','2',' ','2',' ','3',' ','5',' '},        
                                                        {'7','#','5','#','2','#','a','#','2','#','3','#','8','#','1'},        
                                                        {' ','3',' ','2',' ','6',' ','4',' ','2',' ','1',' ','8',' '},         
                                                        {'4','#','6','#','a','#','2','#','a','#','8','#','a','#','8'},         
                                                        {' ','2',' ','5',' ','3',' ','4',' ','1',' ','a',' ','3',' '},         
                                                        {'2','#','z','#','4','#','1','#','9','#','4','#','y','#','5'},          
                                                        {' ','b',' ','3',' ','4',' ','7',' ','a',' ','1',' ','3',' '}};         

    private static final ArrayList<String> direction = new ArrayList<String>();
    private static long paths = 0;
    private static boolean canMove = true;
    private static int localX;
    private static int localY;
    private static int highScore = 0;
    private static String highScorePath = "";
    private static int score;
    private static char[][] grid = new char[gridRef.length][gridRef[0].length];
    private static String currentDirection;
    private static boolean left;
    private static boolean right;
    private static boolean up;
    private static boolean down;
    //Debugging variables
    public static void main(String[] args) {
        direction.add("");//starting point 
        while (true){
            //Move on to next trip
            if (direction.isEmpty()){
                break;
            }else{
                //Continue to travel loop
                for(int z = 0; z < 999999999; z++){
                    currentDirection = direction.get(0);
                    localX = 0;
                    localY = 8;
                    score = 1;
                    setPath(currentDirection);
                    if (!move()){
                        break;
                    }
                    if (z%100 == 0 && z>1){
                        System.out.println("Possible Paths: " + paths + " High score: " + highScore + " Path: " + highScorePath + " size: " + direction.size());
                    }
                }
            }
        }
        System.out.println("Score: " + highScore + " Path: " + highScorePath);
    }
    
  
    public static void setPath(String path){
        grid = newArray();
        char[] temp = path.toCharArray();
        for(int x = 0; x < path.length(); x++){
            switch (temp[x]){
                case 'l':
                    addScore(localY,(localX-1));
                    localX -= 2;
                    break;
                case 'r':
                    addScore(localY,(localX+1));
                    localX += 2;
                    break;
                case 'u':
                    addScore((localY-1),localX);
                    localY -= 2;
                    break;
                case 'd':
                    addScore((localY+1),localX);
                    localY += 2;
                    break;
            }
        }
    }

    public static boolean move(){
        currentDirection = direction.get(0);
        canMove = false;
        left = false;
        right = false;
        up = false;
        down = false;
        //Check left
        try{
            switch (grid[localY][localX-1]){
                case 'x':
                    left = false;break;
                default:
                    left = true;break;
            }
        }catch(ArrayIndexOutOfBoundsException ex){};//surpress exception
        //Check Right
        try{
            switch (grid[localY][localX+1]){
                case 'x':
                    right = false;break;
                default:
                    right = true;break;
            }
        }catch(ArrayIndexOutOfBoundsException ex){};//surpress exception
        //Check Up
        try{
            switch (grid[localY-1][localX]){
                case 'x':
                    up = false;break;
                default:
                    up = true;break;
            }
        }catch(ArrayIndexOutOfBoundsException ex){};//surpress exception
        //Check down
        try{
            switch (grid[localY+1][localX]){
                case 'x':
                    down = false;break;
                default:
                    down = true;break;
            }
        }catch(ArrayIndexOutOfBoundsException ex){};//surpress exception
        //************************************************ Moving *****************************************************//
        /////////////////////////// LEFT ///////////////////////////
        if(left){
            canMove = true;
            addScore(localY,(localX-1));
            localX -= 2;
            if (right){
                direction.add(currentDirection + "r");
            }if (up){
                direction.add(currentDirection + "u");
            }if (down){
                direction.add(currentDirection + "d");
            }
            currentDirection += 'l';
        /////////////////////////// RIGHT ///////////////////////////
        }else if (right){
            canMove = true;
            addScore(localY,(localX+1));
            localX += 2;
            if (up){
                direction.add(currentDirection + "u");
            }if (down){
                direction.add(currentDirection + "d");
            }if (left){
                direction.add(currentDirection + "l");
            }
            currentDirection += 'r';
        /////////////////////////// UP ///////////////////////////
        }else if (up){
            canMove = true;
            addScore((localY-1),localX);
            localY -= 2;
            if (down){
                direction.add(currentDirection + "d");
            }if (left){
                direction.add(currentDirection + "l");
            }if (right){
                direction.add(currentDirection + "r");
            }
            currentDirection += 'u';
        /////////////////////////// DOWN ///////////////////////////
        }else if (down){
            canMove = true;
            addScore((localY+1),localX);
            localY += 2;
            if (left){
                direction.add(currentDirection + "l");
            }if (right){
                direction.add(currentDirection + "r");
            }if (up){
                direction.add(currentDirection + "u");
            }
            currentDirection += 'd';
        }
        //********************** Test for ends *******************************//
        if (!canMove){//If at a dead end
            direction.remove(0);
            return(false);
        }else{ 
            if(localX == 14 && localY == 0){//reached end spot
                if (score > highScore){
                    highScore = score; 
                    highScorePath = currentDirection;
                }
                paths++;
                direction.remove(0);
                return(true);
            }else{
                paths++;
                direction.set(0,currentDirection);
                return(true); //end
            }
        }
        
    }
    public static void addScore(int y, int x){
        char spot = grid[y][x];
        grid[y][x] = 'x';
        switch (spot){
            case '1':
                score++;break;
            case '2':
                score+=2;break;
            case '3':
                score+=3;break;
            case '4':
                score+=4;break;
            case '5':
                score+=5;break;
            case '6':
                score+=6;break;
            case '7':
                score+=7;break;
            case '8':
                score+=8;break;
            case '9':
                score+=9;break;
            case 'a':
                score-=1;break;
            case 'b':
                score-=2;break;
            case 'z':
                score*=2;break;
            case 'y':
                score*=3;break;
            default:
                System.exit(0);
        }
    }
    public static void printArray(char[][] arr){
        for (char[] arr1 : arr) {
            for (char arr2 : arr1){
                System.out.print(arr2 + " ");
            }
            System.out.println();
        }
    }
    
    public static char[][] newArray(){
        char[][] temp = new char[gridRef.length][gridRef[0].length];
        for (int y = 0; y < gridRef.length; y++){
            System.arraycopy(gridRef[y], 0, temp[y], 0, gridRef[0].length);
        }
        return(temp);
    }
    
    public static void debug(int x, int y, String debugMsg,char spot){
        grid[localY][localX] = '▉';
        System.out.println("Error: " + spot + " at (" + (x+1) + "," + (9-y) + ") Debug: " + debugMsg);
        System.out.println("Path: " + currentDirection);
        System.out.println("Left: " + left + " Right: " + right + " Up: " + up + " Down: " + down);
        System.out.println("Coordinates: (" + (localX+1) + "," + (gridRef.length-localY) + ") or " + localX + "," + localY);
        System.out.println("Grid Setup: ");
        printArray(grid);
    }
}


