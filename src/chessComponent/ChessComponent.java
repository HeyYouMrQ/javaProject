package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    protected String name;// 棋子名字：例如 兵，卒，士等
    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if(this instanceof GeneralChessComponent)   this.hierarchy=5;
        else if(this instanceof AdvisorChessComponent)   this.hierarchy=4;
        else if(this instanceof MinisterChessComponent)   this.hierarchy=3;
        else if(this instanceof ChariotChessComponent)   this.hierarchy=2;
        else if(this instanceof HorseChessComponent)   this.hierarchy=1;
        else if(this instanceof CannonChessComponent)   this.hierarchy=1;
        else if(this instanceof SoldierChessComponent)   this.hierarchy=0;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制棋子填充色
        g.setColor(Color.ORANGE);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
       //绘制棋子边框
        g.setColor(Color.DARK_GRAY);
        g.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);

        if (isReversal || isReversalInCheatingMode) {
            //绘制棋子文字
            if(isReversalInCheatingMode && (!isReversal))//todo 这里改写了源码
                g.setColor(this.getChessColor().getColor()==Color.BLACK?Color.gray:Color.MAGENTA);
            else
                g.setColor(this.getChessColor().getColor());
            g.setFont(CHESS_FONT);
            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);

            //绘制棋子被选中时状态
            if (isSelected()) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }
        }
    }
}
