// package it1engine.impl.command;
// import it1engine.enums.CommandType;
// import it1engine.impl.model.command.Command;
// import it1engine.interfaces.command.CommandInterface;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// /**
//  * Unit tests for Command class.
//  */
// class CommandTest {

//     @Test
//     void testConstructorWithParams() {
//         Command cmd = new Command(1000, "P1", CommandType.MOVE, List.of("e2", "e4"));
//         assertEquals(1000, cmd.getTimestamp());
//         assertEquals("P1", cmd.getPieceId());
//         assertEquals("MOVE", cmd.getType());
//         assertEquals(List.of("e2", "e4"), cmd.getParams());
//     }

//     @Test
//     void testConstructorWithoutParams() {
//         Command cmd = new Command(2000, "P2", CommandType.JUMP);
//         assertEquals(2000, cmd.getTimestamp());
//         assertEquals("P2", cmd.getPieceId());
//         assertEquals("JUMP", cmd.getType());
//         assertEquals(List.of(), cmd.getParams());
//     }

//     @Test
//     void testCloneCreatesEqualButIndependentCopy() {
//         Command original = new Command(500, "P3", CommandType.LONG_REST, List.of("a3"));
//         CommandInterface copy = original.clone();

//         assertEquals(original, copy);
//         assertNotSame(original, copy);

//         // Modify original - clone should not change
//         original.addParam("b4");
//         assertNotEquals(original, copy);
//     }

//     @Test
//     void testEqualsAndHashCode() {
//         Command a = new Command(123, "P1", CommandType.MOVE, List.of("x1", "x2"));
//         Command b = new Command(123, "P1", CommandType.MOVE, List.of("x1", "x2"));
//         Command c = new Command(124, "P1", CommandType.MOVE, List.of("x1", "x2"));

//         assertEquals(a, b);
//         assertEquals(a.hashCode(), b.hashCode());

//         assertNotEquals(a, c);
//         assertNotEquals(a.hashCode(), c.hashCode());
//     }

//     @Test
//     void testSettersAndGetters() {
//         Command cmd = new Command(0, "initial", CommandType.IDLE);
//         cmd.setTimestamp(100);
//         cmd.setPieceId("P9");
//         cmd.setType("JUMP");
//         cmd.setParams(List.of("r1", "r2"));

//         assertEquals(100, cmd.getTimestamp());
//         assertEquals("P9", cmd.getPieceId());
//         assertEquals("JUMP", cmd.getType());
//         assertEquals(List.of("r1", "r2"), cmd.getParams());
//         assertEquals(CommandType.JUMP, cmd.getCommandType());
//     }

//     @Test
//     void testAddParamAddsToList() {
//         Command cmd = new Command(300, "P8", CommandType.SHORT_REST);
//         cmd.addParam("start");
//         cmd.addParam("stop");

//         assertEquals(List.of("start", "stop"), cmd.getParams());
//     }

//     @Test
//     void testToStringFormat() {
//         Command cmd = new Command(999, "K9", CommandType.MOVE, List.of("a1", "a2"));
//         String result = cmd.toString();

//         assertTrue(result.contains("timestamp=999"));
//         assertTrue(result.contains("pieceId='K9'"));
//         assertTrue(result.contains("type='MOVE'"));
//         assertTrue(result.contains("params=[a1, a2]"));
//     }

//     @Test
//     void testSetTypeWithInvalidString_throwsException() {
//         Command cmd = new Command(1, "P", CommandType.MOVE);
//         assertThrows(IllegalArgumentException.class, () -> cmd.setType("nonexistent"));
//     }

//     @Test
//     void testConstructorWithNullPieceId_throwsException() {
//         assertThrows(IllegalArgumentException.class, () -> {
//             new Command(1, null, CommandType.MOVE);
//         });
//     }

//     @Test
//     void testConstructorWithNullType_throwsException() {
//         assertThrows(IllegalArgumentException.class, () -> {
//             new Command(1, "P", null);
//         });
//     }

//     @Test
//     void testSetParamsNull_shouldClearParams() {
//         Command cmd = new Command(100, "P", CommandType.LONG_REST, List.of("a", "b"));
//         cmd.setParams(null);
//         assertEquals(List.of(), cmd.getParams());
//     }

//     @Test
//     void testGetType_returnsEnumAsString() {
//         Command cmd = new Command(10, "X", CommandType.JUMP);
//         assertEquals("JUMP", cmd.getType());
//     }

//     @Test
//     void testSetCommandType_directly() {
//         Command cmd = new Command(10, "X", CommandType.MOVE);
//         cmd.setCommandType(CommandType.MOVE);
//         assertEquals("MOVE", cmd.getType());
//     }
// }
