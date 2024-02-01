 class Person {
    public void display() {
        System.out.println("Hi, I am a person!");
    }
    public void display2(){
        System.out.println("Hi, I am a person 2!");
    }
}

class AnonymousDemo {
    public void createClass() {
        // creation of anonymous class extending class Person
        Person person = new Person() {
            public void display() {
                System.out.println("Hi, again !");
            }

        };
        person.display();
        person.display2();
    }

    public static void main(String[] args) {
        AnonymousDemo an = new AnonymousDemo();
        an.createClass();
    }
}
