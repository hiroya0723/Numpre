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
		System.out.println("�e�}�X�̒l����͂��Ă��������B");
		for(i = 0; i < BoardNum; i++){
			for(j = 0; j < BoardNum; j++){
				do{
					System.out.printf("[%d][%d]:",i,j);
					board[i][j] = stdIn.nextInt();
					if(board[i][j] > BoardNum || board[i][j] < 0){
						System.out.printf("0�`%d�܂ł̒l����͂��Ă��������B",BoardNum);
						System.out.println();
					}
				}while(board[i][j] > BoardNum || board[i][j] < 0);
			}
		}
	}
	
	static void showBoard(){
		int i,j;
		System.out.println("�e�}�X�̒l��\�����܂��B");
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
			System.out.println(kaisu + "��ڂł��B");
			for(i = 0; i < BoardNum; i++){			
				if(check_line_number(i) == -1){
					System.out.println("���Ȃ��ł��B");
					return;
				}
				if(check_row_number(i) == -1){
					System.out.println("���Ȃ��ł��B");
					return;
				}
			}
			if(check_box_number() == -1){
				System.out.println("���Ȃ��ł��B");
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
			System.out.println("�v�Z�ł��܂���ł����B");
		else
			System.out.println("flag:"+ flag);
		}while(flag > 0);
		
	}
	
	static int check_line_number(int lineNum){
		int i, j;
		for(i = 0; i < BoardNum - 1; i++){
			for(j = i + 1; j < BoardNum; j++){
				if(board[i][lineNum] == board[j][lineNum] && board[i][lineNum] != 0){
					System.out.printf("board[%d][%d]��board[%d][%d]�̒l�������ł��B",i,lineNum,j,lineNum);
					return -1;
				}
			}
		}
		for(i = 0; i < BoardNum; i++){
			if(board[i][lineNum] == 0){//�}�X���󔒂Ȃ�
				int count = 0;
				System.out.printf("board[%d][%d]�̌���",i,lineNum);
				for(j = 0; j < BoardNum; j++){							
					flag_board[i][lineNum][board[j][lineNum]] = -1;       //�����C���̐�������₩�����
				}
				for(j = 0; j < BoardNum + 1; j++){
					if(flag_board[i][lineNum][j] == 0){
						System.out.print(j + ", ");
						count++;
					}
				}
				flag_board[i][lineNum][possibleCount] = count;
				System.out.println("�ł��B");
			}	
		}
		return 0;
	}
	
	static int check_row_number(int rowNum){
		int i, j;
		for(i = 0; i < BoardNum - 1; i++){
			for(j = i + 1; j < BoardNum; j++){
				if(board[rowNum][i] == board[rowNum][j] && board[rowNum][i] != 0){
					System.out.printf("board[%d][%d]��board[%d][%d]�̒l�������ł��B",rowNum,i,rowNum,j);
					return -1;
				}
			}
		}
		for(i = 0; i < BoardNum; i++){
			if(board[rowNum][i] == 0){//�}�X���󔒂Ȃ�
				int count = 0;
				System.out.printf("board[%d][%d]�̌���",rowNum,i);
				for(j = 0; j < BoardNum; j++){							
					flag_board[rowNum][i][board[rowNum][j]] = -1;       //�����C���̐�������₩�����
				}
				for(j = 0; j < BoardNum + 1; j++){
					if(flag_board[rowNum][i][j] == 0){
						count++;
						System.out.print(j + ", ");
					}
				}
				flag_board[rowNum][i][possibleCount] = count;
				System.out.println("�ł��B");
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
							System.out.print("box���ɓ����l������܂��B");
							System.out.print(board[i+x][j+y]);
							return -1;
						}
					}
				}
				for(x = 0; x < 3; x++){
					for(y = 0; y < 3; y++){
						if(board[i+x][j+y] == 0){
							int count = 0;
							System.out.printf("board[%d][%d]�̌���",i+x,j+y);
							for(k = 0; k <= BoardNum; k++){
								if(flag_boxNumber[k] == -1)
									flag_board[i+x][j+y][k] = flag_boxNumber[k];
								if(flag_board[i+x][j+y][k] == 0){
									count++;
									System.out.print(k + ", ");
								}
							}
						flag_board[i+x][j+y][possibleCount] = count;
						System.out.println("�ł��B");	
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
