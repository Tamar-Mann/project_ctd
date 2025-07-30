package it1engine.impl.observer;


public class DocumentingMove {
    private String pieceId;
    private String from;
    private String to;
    private int timestamp;
    public DocumentingMove(String pieceId, String from, String to, int timestamp) {
        this.pieceId = pieceId;
        this.from = from;
        this.to = to;
        this.timestamp = timestamp;
    }
    public String getPieceId() {
        return pieceId;
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public int getTimestamp() {
        return timestamp;
    }
}





