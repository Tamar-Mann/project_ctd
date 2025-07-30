package it1engine.interfaces.command;

import java.util.List;

import it1engine.impl.model.Moves;

public interface CommandInterface extends Cloneable {
    int getTimestamp();
    String getPieceId();
    String getType(); // Enum name
   List<Moves.Pair> getParams();

    void setTimestamp(int timestamp);
    void setPieceId(String pieceId);
    void setType(String type); // For compatibility (calls valueOf internally)
   void setParams(List<Moves.Pair> params);


     void addParam(Moves.Pair param) ;
        /**
     * Clone the command
     * @return Cloned command instance
     */
    CommandInterface clone();
}
