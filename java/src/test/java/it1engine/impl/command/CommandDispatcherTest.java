
// package it1engine.impl.command;

// import it1engine.interfaces.command.CommandInterface;
// import it1engine.interfaces.command.CommandListenerInterface;
// import it1engine.interfaces.command.CommandDispatcherInterface;
// import it1engine.impl.model.command.CommandDispatcher;
// import it1engine.enums.CommandType;
// import it1engine.mock.MockImg;
// import it1engine.interfaces.ImgInterface;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class CommandDispatcherTest {

//     private CommandDispatcher dispatcher;
//     private MockListener supportedListener;
//     private MockListener unsupportedListener;

//     static class MockCommand implements CommandInterface {
//         private int timestamp;
//         private String pieceId;
//         private String type;
//         private List<String> params;

//         public MockCommand(String type) {
//             this.type = type;
//             this.pieceId = "P1";
//             this.params = List.of("A", "B");
//             this.timestamp = 0;
//         }

//         @Override public int getTimestamp() { return timestamp; }
//         @Override public String getPieceId() { return pieceId; }
//         @Override public String getType() { return type; }
//         @Override public List<String> getParams() { return params; }
//         @Override public void setTimestamp(int timestamp) { this.timestamp = timestamp; }
//         @Override public void setPieceId(String pieceId) { this.pieceId = pieceId; }
//         @Override public void setType(String type) { this.type = type; }
//         @Override public void setParams(List<String> params) { this.params = params; }
//         @Override public void addParam(String param) { this.params.add(param); }
//         @Override public CommandInterface clone() {
//             MockCommand copy = new MockCommand(this.type);
//             copy.setTimestamp(this.timestamp);
//             copy.setPieceId(this.pieceId);
//             copy.setParams(this.params);
//             return copy;
//         }
//     }

//     static class MockListener implements CommandListenerInterface {
//         private final CommandType supportedType;
//         boolean handled = false;

//         MockListener(CommandType supportedType) {
//             this.supportedType = supportedType;
//         }

//         @Override
//         public boolean supports(CommandInterface cmd) {
//             return cmd.getType().equals(supportedType.name());
//         }

//         @Override
//         public void handle(CommandInterface cmd, ImgInterface canvas, long now) {
//             handled = true;
//         }

//         public boolean wasHandled() {
//             return handled;
//         }
//     }

//     @BeforeEach
//     void setUp() {
//         dispatcher = new CommandDispatcher();
//         supportedListener = new MockListener(CommandType.MOVE);
//         unsupportedListener = new MockListener(CommandType.JUMP);
//         dispatcher.register(unsupportedListener);
//         dispatcher.register(supportedListener);
//     }

//     @Test
//     void dispatch_supportedCommand_callsHandle() {
//         MockCommand cmd = new MockCommand(CommandType.MOVE.name());
//         ImgInterface dummyCanvas = new MockImg();
//         long now = System.currentTimeMillis();
//         dispatcher.dispatch(cmd, dummyCanvas, now);
//         assertTrue(supportedListener.wasHandled());
//         assertFalse(unsupportedListener.wasHandled());
//     }

//     @Test
//     void dispatch_unsupportedCommand_throwsException() {
//         MockCommand cmd = new MockCommand(CommandType.IDLE.name()); // no listener supports this
//         ImgInterface dummyCanvas = new MockImg();
//         long now = System.currentTimeMillis();
//         Exception ex = assertThrows(IllegalStateException.class, () -> dispatcher.dispatch(cmd, dummyCanvas, now));
//         assertTrue(ex.getMessage().contains("No listener found"));
//     }

//     @Test
//     void register_addsListenerSuccessfully() {
//         MockListener newListener = new MockListener(CommandType.LONG_REST);
//         dispatcher.register(newListener);
//         MockCommand cmd = new MockCommand(CommandType.LONG_REST.name());
//         ImgInterface dummyCanvas = new MockImg();
//         long now = System.currentTimeMillis();
//         dispatcher.dispatch(cmd, dummyCanvas, now);
//         assertTrue(newListener.wasHandled());
//     }
// }
