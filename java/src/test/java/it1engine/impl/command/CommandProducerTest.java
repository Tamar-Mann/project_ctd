// package it1engine.impl.command;
// import it1engine.impl.model.command.CommandProducer;
// import it1engine.interfaces.command.CommandInterface;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class CommandProducerTest {

//     private final CommandProducer producer = new CommandProducer();

//     @Test
//     void testCreateMoveCommand() {
//         CommandInterface cmd = producer.createMoveCommand("p1", "e2", "e4", 100);
//         assertEquals("p1", cmd.getPieceId());
//         assertEquals("MOVE", cmd.getType());
//         assertEquals(List.of("e2", "e4"), cmd.getParams());
//         assertEquals(100, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateJumpCommand() {
//         CommandInterface cmd = producer.createJumpCommand("p2", "g1", "f3", 200);
//         assertEquals("p2", cmd.getPieceId());
//         assertEquals("JUMP", cmd.getType());
//         assertEquals(List.of("g1", "f3"), cmd.getParams());
//         assertEquals(200, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateIdleCommand() {
//         CommandInterface cmd = producer.createIdleCommand("p3", 300);
//         assertEquals("p3", cmd.getPieceId());
//         assertEquals("IDLE", cmd.getType());
//         assertTrue(cmd.getParams().isEmpty());
//         assertEquals(300, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateLongRestCommand() {
//         CommandInterface cmd = producer.createLongRestCommand("p4", 400);
//         assertEquals("p4", cmd.getPieceId());
//         assertEquals("LONG_REST", cmd.getType());
//         assertTrue(cmd.getParams().isEmpty());
//         assertEquals(400, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateShortRestCommand() {
//         CommandInterface cmd = producer.createShortRestCommand("p5", 500);
//         assertEquals("p5", cmd.getPieceId());
//         assertEquals("SHORT_REST", cmd.getType());
//         assertTrue(cmd.getParams().isEmpty());
//         assertEquals(500, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateGenericCommand_valid() {
//         CommandInterface cmd = producer.createGenericCommand("p6", "MOVE", List.of("a1", "a2"), 600);
//         assertEquals("p6", cmd.getPieceId());
//         assertEquals("MOVE", cmd.getType());
//         assertEquals(List.of("a1", "a2"), cmd.getParams());
//         assertEquals(600, cmd.getTimestamp());
//     }

//     @Test
//     void testCreateGenericCommand_invalidType() {
//         assertThrows(IllegalArgumentException.class, () ->
//                 producer.createGenericCommand("p7", "NOT_A_TYPE", List.of("x", "y"), 700));
//     }

//     @Test
//     void testClonePreservesValues() {
//         CommandInterface original = producer.createJumpCommand("p8", "b1", "c3", 800);
//         CommandInterface copy = original.clone();

//         assertEquals(original, copy);
//         assertNotSame(original, copy); // ensure deep copy
//         assertEquals("p8", copy.getPieceId());
//         assertEquals("JUMP", copy.getType());
//         assertEquals(List.of("b1", "c3"), copy.getParams());
//         assertEquals(800, copy.getTimestamp());
//     }
// }
