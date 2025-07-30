package it1engine.impl.observer;
import java.util.ArrayList;
import java.util.List;

import it1engine.enums.PlayerType;
import it1engine.impl.view.Table;
import it1engine.interfaces.command.CommandInterface;
import it1engine.interfaces.observer.IObserver;

public class DocumentingMovesManagement implements IObserver {


    private List<DocumentingMove> documentingMoves = new ArrayList<>();
    private Table table;
    private PlayerType playerType;


    public List<DocumentingMove> getDocumentingMoves() {
        return documentingMoves;
    }


    @Override
    public void Notify(CommandInterface cmd) {

        String pieceId = cmd.getPieceId();
        String fromPosition = cmd.getParams().size() > 0 ? cmd.getParams().get(0) : "";
        String toPosition = cmd.getParams().size() > 1 ? cmd.getParams().get(1) : "";
        int timestamp = cmd.getTimestamp();

        DocumentingMove move = new DocumentingMove(pieceId, fromPosition, toPosition, timestamp);
        documentingMoves.add(move);

        //playerType
        //table.drawOnBackground();
        

        // Logic to document moves when notified
        // This could involve logging the moves or updating a UI component
        System.out.println("Moves have been documented.");
    }
}

