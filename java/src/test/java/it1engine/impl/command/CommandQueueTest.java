// package it1engine.impl.command;
// import it1engine.enums.CommandType;
// import it1engine.impl.model.command.Command;
// import it1engine.impl.model.command.CommandQueue;
// import it1engine.interfaces.command.CommandInterface;
// import it1engine.interfaces.command.CommandQueueInterface;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// /**
//  * Unit tests for CommandQueue class via its interface.
//  */
// class CommandQueueTest {

//     private CommandQueueInterface queue;

//     @BeforeEach
//     void setUp() {
//         queue = new CommandQueue();
//     }

//     @Test
//     void testQueueInitiallyEmpty() {
//         assertTrue(queue.isEmpty());
//         assertEquals(0, queue.size());
//     }

//     @Test
//     void testAddSingleCommand() {
//         CommandInterface cmd = new Command(100, "P1", CommandType.MOVE);
//         queue.add(cmd);
//         assertFalse(queue.isEmpty());
//         assertEquals(1, queue.size());
//     }

//     @Test
//     void testAddNullCommand_throwsException() {
//         assertThrows(IllegalArgumentException.class, () -> queue.add(null));
//     }

//     @Test
//     void testPollAndPeek() {
//         CommandInterface cmd1 = new Command(10, "P1", CommandType.MOVE);
//         CommandInterface cmd2 = new Command(20, "P2", CommandType.JUMP);

//         queue.add(cmd1);
//         queue.add(cmd2);

//         assertEquals(cmd1, queue.peek());
//         assertEquals(2, queue.size());

//         CommandInterface polled = queue.poll();
//         assertEquals(cmd1, polled);
//         assertEquals(1, queue.size());

//         assertEquals(cmd2, queue.peek());
//     }

//     @Test
//     void testClearEmptiesQueue() {
//         queue.add(new Command(1, "A", CommandType.IDLE));
//         queue.add(new Command(2, "B", CommandType.JUMP));

//         assertFalse(queue.isEmpty());
//         queue.clear();
//         assertTrue(queue.isEmpty());
//     }

//     @Test
//     void testGetAllReturnsSnapshot() {
//         CommandInterface cmd1 = new Command(1, "A", CommandType.MOVE);
//         CommandInterface cmd2 = new Command(2, "B", CommandType.JUMP);

//         queue.add(cmd1);
//         queue.add(cmd2);

//         List<CommandInterface> all = queue.getAll();
//         assertEquals(2, all.size());
//         assertEquals(cmd1, all.get(0));
//         assertEquals(cmd2, all.get(1));

//         queue.poll();
//         assertEquals(2, all.size()); // snapshot should not change
//     }

//     @Test
//     void testPopUntil_removesCorrectSubset() {
//         CommandInterface c1 = new Command(100, "P1", CommandType.MOVE);
//         CommandInterface c2 = new Command(200, "P2", CommandType.JUMP);
//         CommandInterface c3 = new Command(300, "P3", CommandType.LONG_REST);

//         queue.add(c1);
//         queue.add(c2);
//         queue.add(c3);

//         List<CommandInterface> popped = queue.popUntil(200);
//         assertEquals(List.of(c1, c2), popped);
//         assertEquals(1, queue.size());
//         assertEquals(c3, queue.peek());
//     }

//     @Test
//     void testPopUntilWithNoMatch_returnsEmptyList() {
//         queue.add(new Command(500, "X", CommandType.IDLE));
//         List<CommandInterface> result = queue.popUntil(100);
//         assertTrue(result.isEmpty());
//         assertEquals(1, queue.size());
//     }

//     @Test
//     void testPopUntilOnEmptyQueue_returnsEmptyList() {
//         List<CommandInterface> result = queue.popUntil(1000);
//         assertNotNull(result);
//         assertTrue(result.isEmpty());
//     }
// }
