import junit.framework.TestCase;
import org.junit.Test;

public class MyQueueTest extends TestCase {

    /////////////////////////////////////////////////////////////////////////////
    //Push & Size
    /////////////////////////////////////////////////////////////////////////////
 
    @Test
    public void testSizeAndPushBasic() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertEquals(3,s.getMaximumDelay());
        assertEquals(3,s.getDelay());

        s.enqueue("alfa");
        assertEquals(1,s.size());
        s.enqueue("beta");
        assertEquals(2,s.size());

        assertEquals(1,s.getDelay());

        assertNull(s.dequeue());
        s.enqueue("cat");

        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
    }

    @Test
    public void testPushExpandSize() {
        DelayedQueue<String> s = new MyQueue<String>(3);

        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");

        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
    }
    
    @Test
    public void testPushDelayReset() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertEquals(3,s.getMaximumDelay());
        assertEquals(3,s.getDelay());

        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");

        assertEquals(0,s.getDelay());

        assertEquals("alfa",s.dequeue());
        assertEquals(0,s.getDelay());

        s.enqueue("delta");
        assertEquals(2,s.getDelay());
    }
    /////////////////////////////////////////////////////////////////////////////
    //dequeue
    /////////////////////////////////////////////////////////////////////////////

    @Test
    public void testPopUnderflow() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        try{
            s.dequeue();
        }catch(IllegalStateException e){
            return;
        }
        fail("Exception was not thrown");
    }

    @Test
    public void testPopLocked() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        s.enqueue("alfa");
        assertNull(s.dequeue());

        s.enqueue("beta");
        assertNull(s.dequeue());
        
        s.enqueue("cat");
        assertEquals("alfa",s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
    }

    @Test
    public void testPopNullElem() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        s.enqueue(null);
        s.enqueue("beta");
        s.enqueue("cat");
        s.enqueue(null);
        s.enqueue("beta");

        assertNull(s.dequeue());
        assertEquals("beta",s.dequeue());
        assertEquals("cat",s.dequeue());
    }

    @Test
    public void testPopNullElem2() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        s.enqueue(null);
        assertNull(s.dequeue());
        assertEquals(1,s.size());

        s.enqueue(null);
        assertNull(s.dequeue());
        assertEquals(2,s.size());

        s.enqueue(null);
        assertEquals(3,s.size());

        assertNull(s.dequeue());
        assertEquals(2,s.size());
    }

    /////////////////////////////////////////////////////////////////////////////
    //Peek
    /////////////////////////////////////////////////////////////////////////////

    @Test
    public void testPeekBasic() {
        DelayedQueue<String> s = new MyQueue<String>(5);
        s.enqueue("alfa");
        assertEquals("alfa",s.peek());

        s.enqueue("beta");
        assertEquals("alfa",s.peek());

        s.enqueue("cat");
        assertEquals("alfa",s.peek());
    }

    @Test
    public void testPeekException() {
        DelayedQueue<String> s = new MyQueue<String>(5);
        try{
            s.peek();
        }catch(IllegalStateException e){
            return;
        }
        fail("Exception was not thrown");
    }

    @Test
    public void testPeekException2() {
        DelayedQueue<String> s = new MyQueue<String>(5);
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");

        s.dequeue();
        s.dequeue();
        s.dequeue();
        s.dequeue();
        s.dequeue();
        s.dequeue();

        try{
            s.peek();
        }catch(IllegalStateException e){
            return;
        }
        fail("Exception was not thrown");
    }

    /////////////////////////////////////////////////////////////////////////////
    //getDelay
    /////////////////////////////////////////////////////////////////////////////

    @Test
    public void testgetDelay() {
        DelayedQueue<String> s = new MyQueue<String>(5);
        assertEquals(5,s.getDelay());
        s.enqueue("alfa");
        assertEquals(4,s.getDelay());
        s.enqueue("beta");

        s.dequeue();
        assertEquals(3,s.getDelay());

        assertEquals(3,s.getDelay());
        s.enqueue("cat");
        assertEquals(2,s.getDelay());
        s.enqueue("alfa");
        assertEquals(1,s.getDelay());
        s.enqueue("beta");
        assertEquals(0,s.getDelay());
        s.enqueue("cat");
        assertEquals(0,s.getDelay());

        s.dequeue();
        assertEquals(0,s.getDelay());

        s.enqueue("alfa");
        assertEquals(4,s.getDelay());
        s.enqueue("beta");
        assertEquals(3,s.getDelay());
    }

    /////////////////////////////////////////////////////////////////////////////
    /////setMaximumDelay & getMaximumDelay
    /////////////////////////////////////////////////////////////////////////////

    @Test
    public void testSetandGetMaximumDelay() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertEquals(3,s.getMaximumDelay());
        assertEquals(3,s.getDelay());

        s.enqueue("alfa");
        s.enqueue("beta");
        s.setMaximumDelay(5);
        assertEquals(5,s.getMaximumDelay());
        assertEquals(1,s.getDelay());
        
        s.enqueue("cat");
        assertEquals(5,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        assertEquals("alfa",s.dequeue());
        assertEquals(5,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        s.enqueue("dogge");
        assertEquals(5,s.getMaximumDelay());
        assertEquals(4,s.getDelay());
    }

    @Test
    public void testSetandGetMaximumDelay2() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertEquals(3,s.getMaximumDelay());
        assertEquals(3,s.getDelay());

        s.enqueue("alfa");
        s.enqueue("beta");
        s.setMaximumDelay(5);

        assertEquals(5,s.getMaximumDelay());
        assertEquals(1,s.getDelay());
        
        s.enqueue("cat");
        assertEquals(5,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        assertEquals("alfa",s.dequeue());
        assertEquals(5,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        s.setMaximumDelay(10);

        s.enqueue("dogge");
        assertEquals(10,s.getMaximumDelay());
        assertEquals(9,s.getDelay());
    }

    @Test
    public void testSetandGetMaximumDelay3() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertEquals(3,s.getMaximumDelay());
        assertEquals(3,s.getDelay());

        s.enqueue("alfa");
        s.enqueue("beta");
        s.setMaximumDelay(5);

        assertEquals(5,s.getMaximumDelay());
        assertEquals(1,s.getDelay());
        
        s.enqueue("cat");
        assertEquals(5,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        s.setMaximumDelay(15);

        assertEquals("alfa",s.dequeue());
        assertEquals(15,s.getMaximumDelay());
        assertEquals(0,s.getDelay());

        s.enqueue("dogge");
        assertEquals(15,s.getMaximumDelay());
        assertEquals(14,s.getDelay());
    }

    /////////////////////////////////////////////////////////////////////////////
    //clear
    /////////////////////////////////////////////////////////////////////////////

    @Test
    public void testClearBasic() {
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertTrue(s.clear());
        s.enqueue("alfa");
        assertFalse(s.clear());
        s.enqueue("beta");
        assertFalse(s.clear());
        s.enqueue("cat");
        assertEquals(3,s.size());
        assertTrue(s.clear());
        assertEquals(0,s.size());
    }

    /////////////////////////////////////////////////////////////////////////////
    //contains
    /////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void testContainsBasic(){
        DelayedQueue<String> s = new MyQueue<String>(3);
        s.enqueue("alfa");
        s.enqueue("beta");
        s.enqueue("cat");
        assertTrue(s.contains("alfa"));
        assertTrue(s.contains("beta"));
        assertFalse(s.contains("dogge"));
        assertFalse(s.contains(null));
    }

    @Test
    public void testContainsNull(){
        DelayedQueue<String> s = new MyQueue<String>(3);
        assertFalse(s.contains("alfa"));
        assertFalse(s.contains(null));

        s.enqueue("alfa");
        assertTrue(s.contains("alfa"));

        s.enqueue(null);
        assertTrue(s.contains(null));
        s.enqueue(null);

        s.dequeue();
        assertTrue(s.contains(null));
        s.dequeue();
        assertTrue(s.contains(null));
        s.dequeue();
        assertFalse(s.contains(null));
    }
}

