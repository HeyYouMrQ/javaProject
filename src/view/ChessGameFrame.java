package view;

import chessComponent.*;
import controller.GameController;
import model.ChessColor;
import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;

    private GameController gameController;
    private static JLabel statusLabel;
    private static JLabel scoreOfBlack;
    private static JLabel scoreOfRed;
    public static Chessboard chessboard;
    public static Stack<Integer> ope=new Stack<>(),firCom=new Stack<>(),firCol=new Stack<>(),firX=new Stack<>(),firY=new Stack<>()
            ,secCom=new Stack<>(),secCol=new Stack<>(),secX=new Stack<>(),secY=new Stack<>(),firCannonSecRev=new Stack<>();//ope=1,翻棋子,仅用fir;=2,走棋子,fir与sec都用;
    // firCannonSecRev若first为炮吃了未翻开的棋子，则为1
    @Override
    public Container getContentPane() {
        return super.getContentPane();
    }

    public static JLabel getScoreOfBlack() {
        return scoreOfBlack;
    }

    public static JLabel getScoreOfRed() {
        return scoreOfRed;
    }
    public ChessGameFrame(int width,int height) {
        setTitle("暗棋"); //设置标题
        this.WIDTH=width;
        this.HEIGHT=height;
        this.CHESSBOARD_SIZE=HEIGHT*4/5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addLabel();
    //    addHelloButton();
        addRestartButton();//todo
        addCheatingModeButton();
        addLoadButton();
        addWithdrawButton();
        addChessboard();
    }

    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE/2,CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("BLACK's TURN");
        statusLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("宋体", Font.BOLD, 20));
        add(statusLabel);

        scoreOfBlack = new JLabel(String.format("BLACK's points: %d", Chessboard.blackPlayer.getCurrentScore()));
        scoreOfBlack.setLocation(WIDTH * 3 / 5, HEIGHT *2/ 10);
        scoreOfBlack.setSize(200, 60);
        scoreOfBlack.setFont(new Font("宋体", Font.BOLD, 20));
        add(scoreOfBlack);

        scoreOfRed = new JLabel(String.format("RED's points: %d", Chessboard.redPlayer.getCurrentScore()));
        scoreOfRed.setLocation(WIDTH * 3 / 5, HEIGHT *3/ 10);
        scoreOfRed.setSize(200, 60);
        scoreOfRed.setFont(new Font("宋体", Font.BOLD, 20));
        add(scoreOfRed);

    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */
/*    private void addHelloButton() {//todo
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 120);
        button.setSize(180, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);
    }*/
    private void addRestartButton() {
        JButton button = new JButton("RESTART");
        button.addActionListener((e) -> {
            chessboard.initAllChessOnBoard();//restart!
            ChessGameFrame.repaintAll();
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 420);
        button.setSize(180, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        add(button);
    }
    public static JButton cheatingButton = new JButton("Cheating Mode: OFF");
    private void addCheatingModeButton() {//todo
        cheatingButton.addActionListener((e) -> {
            if(cheatingButton.getText().equals("Cheating Mode: OFF")) {
                cheatingButton.setText("Cheating Mode: ON");
                cheatingButton.repaint();
                //todo
            }
            else {
                cheatingButton.setText("Cheating Mode: OFF");
                cheatingButton.repaint();
                //todo
            }
        });
        cheatingButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 320);
        cheatingButton.setSize(230, 60);
        cheatingButton.setFont(new Font("宋体", Font.BOLD, 20));
        add(cheatingButton);
    }
    public static JButton withdrawButton = new JButton("Cheating Mode: OFF");
    private void withdraw()
    {//todo
        SquareComponent firChess = chessboard.getChessComponents()[firX.peek()][firY.peek()];
        if(ope.peek()==1)//翻棋子的
            firChess.setReversal(false);
        else if(ope.peek()==2)//走棋子的 0-6:将士相车马兵炮7:空
        {
            SquareComponent secChess = chessboard.getChessComponents()[secX.peek()][secY.peek()];
            if(firCom.peek()==0)
            firChess = new GeneralChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==1)
            firChess = new AdvisorChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==2)
            firChess = new MinisterChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==3)
            firChess = new ChariotChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==4)
            firChess = new HorseChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==5)
            firChess = new SoldierChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(firCom.peek()==6)
            firChess = new CannonChessComponent(new ChessboardPoint(firX.peek(),firY.peek()), chessboard.calculatePoint(firX.peek(),firY.peek())
                    , firCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            //else if(firCom.peek()==7) first 必无空棋子
            //firChess = new EmptySlotComponent(firChess.getChessboardPoint(), firChess.getLocation(), chessboard.clickController, chessboard.CHESS_SIZE);

            if(secCom.peek()==0)
                secChess = new GeneralChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==1)
                secChess = new AdvisorChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==2)
                secChess = new MinisterChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==3)
                secChess = new ChariotChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==4)
                secChess = new HorseChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==5)
                secChess = new SoldierChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==6)
                secChess = new CannonChessComponent(new ChessboardPoint(secX.peek(),secY.peek()), chessboard.calculatePoint(secX.peek(),secY.peek())
                        , secCol.peek()==0?ChessColor.BLACK:ChessColor.RED , chessboard.clickController, chessboard.CHESS_SIZE);
            else if(secCom.peek()==7)
                secChess = new EmptySlotComponent(secChess.getChessboardPoint(), secChess.getLocation(), chessboard.clickController, chessboard.CHESS_SIZE);

            chessboard.putChessOnBoard(firChess);
            chessboard.putChessOnBoard(secChess);

            firChess.setReversal(true);
            if(firCannonSecRev.peek()==1)
                secChess.setReversal(false);
            else
                secChess.setReversal(true);

            if(secCol.peek()==0)
                Chessboard.redPlayer.setCurrentScore(Chessboard.redPlayer.getCurrentScore()-secChess.score);
            else
                Chessboard.blackPlayer.setCurrentScore(Chessboard.blackPlayer.getCurrentScore()-secChess.score);
            ChessGameFrame.getScoreOfBlack().setText(String.format("BLACK's points: %d", Chessboard.blackPlayer.getCurrentScore()));
            ChessGameFrame.getScoreOfRed().setText(String.format("RED's points: %d", Chessboard.redPlayer.getCurrentScore()));
        }
        Chessboard.setCurrentColor(Chessboard.getCurrentColor()==ChessColor.RED?ChessColor.BLACK:ChessColor.RED);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", Chessboard.getCurrentColor().getName()));
        ope.pop();
        firCom.pop();   firCol.pop();   firX.pop(); firY.pop();
        secCom.pop();   secCol.pop();   secX.pop(); secY.pop(); firCannonSecRev.pop();
        repaintAll();
        if(ope.empty())
            withdrawButton.setEnabled(false);
    }
    private void addWithdrawButton() {
        withdrawButton.setText("withdraw");
        withdrawButton.setEnabled(false);
        withdrawButton.addActionListener((e) -> {
            withdraw();
        });
        withdrawButton.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 500);
        withdrawButton.setSize(180, 60);
        withdrawButton.setFont(new Font("宋体", Font.BOLD, 20));
        add(withdrawButton);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 240);
        button.setSize(180, 60);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }
    public static void repaintAll()
    {
        withdrawButton.repaint();
        chessboard.repaint();
        statusLabel.repaint();
        scoreOfBlack.repaint();
        scoreOfRed.repaint();
        cheatingButton.repaint();
    }
}