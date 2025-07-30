package it1engine.impl.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import it1engine.impl.model.Board;
import it1engine.impl.observer.DocumentingMove;

public class Table {

    List<DocumentingMove> documentingMovesManagementList = new ArrayList<>();
    private int wPix;
    private int hPix;


    // public void Show(PlayerType playerType) {
    //     for (DocumentingMovesManagement documentingMovesManagement : documentingMovesManagementList) {
    //         if(playerType== PlayerType.Plyer1) {
    //             System.out.println("Black:");
    //         } else if(playerType == PlayerType.Player2) {
    //             System.out.println("White:");
    //         } else {
    //             System.out.println("Unknown Player Type");
    //         }   
    //         System.out.println("Documenting Moves Management:");

    //         for (DocumentingMove move : documentingMovesManagement.getDocumentingMoves()) {

    //             System.out.println(" - Move: " + move.getFrom() + " to " + move.getTo() + " by piece " + move.getPieceId() + " at timestamp " + move.getTimestamp());
    //         }
    //     }
    // }

    public Table(List<DocumentingMove> documentingMovesManagementList, int wPix, int hPix) {
        this.documentingMovesManagementList = documentingMovesManagementList;
        this.wPix = wPix;
        this.hPix = hPix;
    }
    
    public void drawOnBackground(Board background) {
        if (background == null || background.getImg() == null || documentingMovesManagementList == null || documentingMovesManagementList.isEmpty())
            return;
        //Img img = background.getImg();
        int startX = 0;
        int startY = 0;
        int totalW = wPix;
        int totalH = hPix;
        int rowHeight = 20;
        float fontSize = 1.2f;
        Color textColor = Color.BLACK;   
        int maxRows = totalH / rowHeight;
        int displayedRows = Math.min(documentingMovesManagementList.size(), maxRows);
        for (int i = 0; i < displayedRows; i++) {
            DocumentingMove move = documentingMovesManagementList.get(i);
            if (move == null)
                continue;
            int y = startY + i * rowHeight;
            //img.putText("pieceId: " + move.getPieceId(), startX + 10, y, fontSize, textColor, 1);
            //img.putText("from: " + move.getFrom(), startX + 150, y, fontSize, textColor, 1);
            //img.putText("to: " + move.getTo(), startX + 300, y, fontSize, textColor, 1);
            //img.putText("timestamp: " + move.getTimestamp(), startX + 450, y, fontSize, textColor, 1);
        }
    }
    // @Override
    // public void drawOnBackGround(BackGround, int nowMs) {
    //     // 1. קבל את מיקום הכלי (שורה, עמודה) מתוך ה-physics
    //     int[] cell = this.state.getPhysics().getPos(); // [row, col]
    //     // 2. המר למיקום פיקסלי
    //     int xPix = cell[1] * board.getCellWPix();
    //     int yPix = cell[0] * board.getCellHPix();
    //     // 3. קבל את הספרייט הנוכחי מתוך ה-state
    //     ImgInterface sprite = this.state.getCurrentSprite(nowMs); // מימוש נדרש ב-State שלך
    //     // 4. צייר אותו על הלוח
    //     sprite.drawOn(board.getImg(), xPix, yPix);
    // }


}
