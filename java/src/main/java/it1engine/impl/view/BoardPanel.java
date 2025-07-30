// package it1engine.impl.view;
// import it1engine.impl.Board;
// import it1engine.interfaces.BoardInterface;
// import it1engine.interfaces.CommandInterface;
// import it1engine.interfaces.GraphicsInterface;
// import it1engine.interfaces.PieceInterface;
// import it1engine.interfaces.StateInterface;
// import it1engine.impl.state.State;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.awt.image.BufferedImage;
// import java.util.HashMap;
// import java.util.Map;

// public class BoardPanel extends JPanel {


//     private int selectedRow = 0;
//     private int selectedCol = 0;


//     private final static int CELL_SIZE = 64;
//     private Board board;

//     private Map<String, Piece> allPieces = new HashMap<>();


//     public void setAllPieces(Map<String, Piece> allPieces) {
//         this.allPieces = allPieces;
//         repaint(); // redraw the board when pieces are set
//     }

// //    public BoardPanel(Board board) {
// //        this.board = board;
// //    }


//     public BoardPanel(Board board) {
//         this.board = board;
//         setFocusable(true);
//         requestFocusInWindow();

//         addKeyListener(new KeyAdapter() {
//             @Override
//             public void keyPressed(KeyEvent e) {
//                 switch (e.getKeyCode()) {
//                     case KeyEvent.VK_LEFT:
//                         if (selectedCol > 0) selectedCol--;
//                         break;
//                     case KeyEvent.VK_RIGHT:
//                         if (selectedCol < 7) selectedCol++;
//                         break;
//                     case KeyEvent.VK_UP:
//                         if (selectedRow > 0) selectedRow--;
//                         break;
//                     case KeyEvent.VK_DOWN:
//                         if (selectedRow < 7) selectedRow++;
//                         break;
//                 }
//                 repaint();
//             }
//         });
//     }


//     public BoardPanel() {
//         try {
//             String path = getClass().getClassLoader().getResource("assets/board.png").getPath();
//             Img img = new Img().read(path, new Dimension(512, 512), true, null);

//             board = new Board(
//                     CELL_SIZE, CELL_SIZE,
//                     1, 1,      // meters, dummy values
//                     8, 8,      // 8x8 grid
//                     img
//             );
//         } catch (Exception e) {
//             System.err.println("Failed to load board image: " + e.getMessage());
//         }
//     }

// //    @Override
// //    protected void paintComponent(Graphics g) {
// //        super.paintComponent(g);
// //
// //        if (board != null && board.getImg() != null) {
// //            g.drawImage(board.getImg().get(), 0, 0, null);
// //        } else {
// //            g.setColor(Color.RED);
// //            g.drawString("Board image not found", 10, 20);
// //        }
// //    }

// //    @Override
// //    protected void paintComponent(Graphics g) {
// //        super.paintComponent(g);
// //
// //        if (board != null && board.getImg() != null) {
// //            g.drawImage(board.getImg().get(), 0, 0, null);
// //        } else {
// //            g.setColor(Color.RED);
// //            g.drawString("Board image not found", 10, 20);
// //        }
// //
// ////        for (Piece piece : allPieces.values()) {
// ////            int x = piece.getCol() * CELL_SIZE;
// ////            int y = piece.getRow() * CELL_SIZE;
// ////            g.drawImage(piece.getDefaultImage(), x, y, CELL_SIZE, CELL_SIZE, null);
// ////        }
// //
// //        for (Piece piece : allPieces.values()) {
// //            BufferedImage img = piece.getDefaultImage();
// //            if (img == null) {
// //                System.out.println("Warning: getDefaultImage() returned null for piece: row=" + piece.getRow() + ", col=" + piece.getCol());
// //                continue; // skip rendering
// //            }
// //
// //            int x = piece.getCol() * CELL_SIZE;
// //            int y = piece.getRow() * CELL_SIZE;
// //            g.drawImage(img, x, y, CELL_SIZE, CELL_SIZE, null);
// //        }
// //    }


//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);

//         if (board != null && board.getImg() != null) {
//             g.drawImage(board.getImg().get(), 0, 0, null);
//         } else {
//             g.setColor(Color.RED);
//             g.drawString("Board image not found", 10, 20);
//         }

//         for (Piece piece : allPieces.values()) {
//             BufferedImage img = piece.getDefaultImage();
//             if (img == null) {
//                 System.out.println("Warning: getDefaultImage() returned null for piece: row=" + piece.getRow() + ", col=" + piece.getCol());
//                 continue;
//             }

//             int x = piece.getCol() * CELL_SIZE;
//             int y = piece.getRow() * CELL_SIZE;
//             g.drawImage(img, x, y, CELL_SIZE, CELL_SIZE, null);
//         }

//         // Draw blue selection rectangle
//         g.setColor(Color.BLUE);
//         ((Graphics2D) g).setStroke(new BasicStroke(3));
//         g.drawRect(selectedCol * CELL_SIZE, selectedRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
//     }



//     @Override
//     public Dimension getPreferredSize() {
//         if (board != null && board.getImg() != null) {
//             BufferedImage img = board.getImg().get(); // not getImage(), just get()
//             return new Dimension(img.getWidth(), img.getHeight());
//         }
//         return new Dimension(822, 828);  // fallback
//     }

// }