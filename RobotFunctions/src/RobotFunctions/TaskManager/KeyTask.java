package RobotFunctions.TaskManager;

public abstract class KeyTask extends Task{
    protected KeyTask(Clock clock, Controller controller) {
        super(clock);
        this.controller = controller;
    }

    protected abstract double[] keyMapping();

    public abstract static class Toggle extends Thread{
        public Toggle(Controller controller, Clock clock) {
            this.controller = controller;
            this.state = false;
            this.clock = clock;
        }

        protected abstract boolean testKey();

        @Override
        public void run() {
            while(!super.isInterrupted()) {
                while (!super.isInterrupted() && !this.testKey());

                this.state = this.state ? false : true;

                while (!super.isInterrupted() && this.testKey());
            }
        }
        public void terminate() {
            this.interrupt();
        }

        public boolean getToggleState() {
            return this.state;
        }

        private Clock clock;
        private boolean state;
        protected Controller controller;
    }

    protected Controller controller;
}

//todo add documentation
