import java.util.Scanner;

public class Numpre {
	final static int BoardNum = 9;
	final static int possibleCount = BoardNum + 1;
	static int[][] board = new int[BoardNum][BoardNum];
    static int[][][] flag_board = new int [BoardNum][BoardNum][BoardNum + 2];
    static int flag = 2;
    //static boolean end_flag = true;
    
	public static void main(String[] args){
				
		initBoard();
		showBoard();
		calcBoard();
		//result();		
	}
	
	static void initBoard(){
		Scanner stdIn = new Scanner(System.in);
		
		int i,j;
		System.out.println("各マスの値を入力してください。");
		for(i = 0; i < BoardNum; i++){
			for(j = 0; j < BoardNum; j++){
				do{
					System.out.printf("[%d][%d]:",i,j);
					board[i][j] = stdIn.nextInt();
					if(board[i][j] > BoardNum || board[i][j] < 0){
						System.out.printf("0～%dまでの値を入力してください。",BoardNum);
						System.out.println();
					}
				}while(board[i][j] > BoardNum || board[i][j] < 0);
			}
		}
	}
	
	static void showBoard(){
		int i,j;
		System.out.println("各マスの値を表示します。");
		for(i = 0; i < BoardNum; i++){
			for(j = 0; j < BoardNum; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	static void calcBoard(){
		int i,j,k;
		int kaisu = 0;
		do{
			kaisu++;
			System.out.println(kaisu + "回目です。");
			for(i = 0; i < BoardNum; i++){			
				if(check_line_number(i) == -1){
					System.out.println("解なしです。");
					return;
				}
				if(check_row_number(i) == -1){
					System.out.println("解なしです。");
					return;
				}
			}
			if(check_box_number() == -1){
				System.out.println("解なしです。");
				return;
			}
			
			for(i = 0; i < BoardNum; i++){
				for(j = 0; j < BoardNum; j++){
					if(flag_board[i][j][possibleCount] != 0){
						if(flag_board[i][j][possibleCount] == 1){
							for(k = 1; k <= BoardNum; k++){
								if(flag_board[i][j][k] == 0){
									board[i][j] = k;
									flag++;
								}
							}
						flag_board[i][j][possibleCount] = 0;
						}
					}
				}
			}
		flag--;
		showBoard();
		if(flag <= 0)
			System.out.println("計算できませんでした。");
		else
			System.out.println("flag:"+ flag);
		}while(flag > 0);
		
	}
	
	static int check_line_number(int lineNum){
		int i, j;
		for(i = 0; i < BoardNum - 1; i++){
			for(j = i + 1; j < BoardNum; j++){
				if(board[i][lineNum] == board[j][lineNum] && board[i][lineNum] != 0){
					System.out.printf("board[%d][%d]とboard[%d][%d]の値が同じです。",i,lineNum,j,lineNum);
					return -1;
				}
			}
		}
		for(i = 0; i < BoardNum; i++){
			if(board[i][lineNum] == 0){//マスが空白なら
				int count = 0;
				System.out.printf("board[%d][%d]の候補は",i,lineNum);
				for(j = 0; j < BoardNum; j++){							
					flag_board[i][lineNum][board[j][lineNum]] = -1;       //同ラインの数字を候補から消す
				}
				for(j = 0; j < BoardNum + 1; j++){
					if(flag_board[i][lineNum][j] == 0){
						System.out.print(j + ", ");
						count++;
					}
				}
				flag_board[i][lineNum][possibleCount] = count;
				System.out.println("です。");
			}	
		}
		return 0;
	}
	
	static int check_row_number(int rowNum){
		int i, j;
		for(i = 0; i < BoardNum - 1; i++){
			for(j = i + 1; j < BoardNum; j++){
				if(board[rowNum][i] == board[rowNum][j] && board[rowNum][i] != 0){
					System.out.printf("board[%d][%d]とboard[%d][%d]の値が同じです。",rowNum,i,rowNum,j);
					return -1;
				}
			}
		}
		for(i = 0; i < BoardNum; i++){
			if(board[rowNum][i] == 0){//マスが空白なら
				int count = 0;
				System.out.printf("board[%d][%d]の候補は",rowNum,i);
				for(j = 0; j < BoardNum; j++){							
					flag_board[rowNum][i][board[rowNum][j]] = -1;       //同ラインの数字を候補から消す
				}
				for(j = 0; j < BoardNum + 1; j++){
					if(flag_board[rowNum][i][j] == 0){
						count++;
						System.out.print(j + ", ");
					}
				}
				flag_board[rowNum][i][possibleCount] = count;
				System.out.println("です。");
			}	
		}
		return 0;
	}
	
	static int check_box_number(){
		int i,j;
		int x,y;
		int k;
		int[] flag_boxNumber = new int[BoardNum + 1];
		for(i = 0; i < BoardNum; i += 3){
			for(j = 0; j < BoardNum; j += 3){
				for(x = 0; x < 3; x++){
					for(y = 0; y < 3; y++){
						if(flag_boxNumber[board[i+x][j+y]] != -1)
							flag_boxNumber[board[i+x][j+y]] = -1;
						else if(board[i+x][j+y] == 0)
							;
						else{
							System.out.print("box内に同じ値があります。");
							System.out.print(board[i+x][j+y]);
							return -1;
						}
					}
				}
				for(x = 0; x < 3; x++){
					for(y = 0; y < 3; y++){
						if(board[i+x][j+y] == 0){
							int count = 0;
							System.out.printf("board[%d][%d]の候補は",i+x,j+y);
							for(k = 0; k <= BoardNum; k++){
								if(flag_boxNumber[k] == -1)
									flag_board[i+x][j+y][k] = flag_boxNumber[k];
								if(flag_board[i+x][j+y][k] == 0){
									count++;
									System.out.print(k + ", ");
								}
							}
						flag_board[i+x][j+y][possibleCount] = count;
						System.out.println("です。");	
						}
					}
				}
				for(k = 0; k <= BoardNum; k++){
					flag_boxNumber[k] = 0;
				}
			}
		}
		return 0;
	}
								
						
}
