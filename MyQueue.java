public class MyQueue<E> implements DelayedQueue<E>{
    
	private E[] queue = (E[]) new Object[100];
	private int MaximumDelay;
	private int delay;
	private int size;
	private boolean flag = false;

	public MyQueue(int Max){
		if (Max<1) Max=0;
		this.MaximumDelay = Max;
		delay = Max;
		size = 0;
	}

	@Override
	public int size(){
		return size;
	}
	
	@Override
	public void enqueue(E element){
		if (flag) {
			flag = false;
			if(MaximumDelay<1)
				delay = 0;
			else
				delay = MaximumDelay;	
        }

		if(size == queue.length){
			E[] newQueue = (E[]) new Object[size*2];
			System.arraycopy(queue,0,newQueue,0,size);
			queue = newQueue;
		}
	
		queue[size] = element;
		size++;
		delay--;
	}
	
	@Override
	public E dequeue() throws IllegalStateException{
		if(size()==0)
			throw new IllegalStateException();
		if(getDelay()==0 || flag){
			if(queue[0]==null) {
				size--;
				flag = true;
				for(int i=0;i<size;i++)
					queue[i] = queue[i+1];
				queue[size]=null;
				return null;
			}
			E e = queue[0];
			queue[0] = null;
			size--;
			flag = true;
			for(int i=0;i<size;i++)
				queue[i] = queue[i+1];
			queue[size]=null;
			return e;
		}else{
			return null;
		}
		
	}
	
	@Override
	public E peek() throws IllegalStateException{
		if(size == 0)
			throw new IllegalStateException();
		E e = queue[0];
		return e;
	}
	
	@Override
	public int getDelay(){
		if(delay<0) delay = 0;
		return delay;
	}
	
	@Override
	public void setMaximumDelay(int d){
		MaximumDelay = d;
	}
	
	@Override
	public int getMaximumDelay(){
        return MaximumDelay;
	}
	
	@Override
	public boolean clear(){
		if(size==0) return true;
		if(getDelay()==0){
			for(int i=0;i<size;i++)
				queue[i] = null;
			size = 0;
			flag = true;
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean contains(E elem){
		if(size==0) return false;
		for(int i=0;i<size;i++){
			if(elem==null && queue[i] == null) return true;
			else if(elem==null || queue[i] == null) continue;
			else if(elem.equals(queue[i])) return true;
		}
		return false;
	}
	
	// @Override
	// public void printall(){
	// 	System.out.print(size());
	// 	System.out.print(" (");
	// 	for(int i=0;i<size;i++){
	// 		System.out.print(queue[i]+" ");
	// 	}
	// 	System.out.println(")");
	// }

	public static void main(String[] args) {
		DelayedQueue<String> s = new MyQueue<String>(4); //delay condition of 4
		s.enqueue("first element");
		s.enqueue("something else");
		s.dequeue(); //return value is null, because so far only 2 elements have been pushed
		s.enqueue("third");
		s.enqueue("fourth");
		s.dequeue(); //return value is “first element”
		s.enqueue("another one");
		s.dequeue(); //return value is null again, because the delay condition has been reset
		s.enqueue("2");
		s.enqueue("3");
		s.enqueue("4");
		s.dequeue(); // return value is “something else”
		s.dequeue(); // return value is “third”
		s.dequeue(); s.dequeue(); s.dequeue(); s.dequeue();
		//return values are “fourth”, “another one”, “2”, “3” 
    }
}

