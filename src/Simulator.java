public class Simulator {

    public static void main(String[] args) {


        String[] inputs = {"a_example", "b_should_be_easy", "c_no_hurry", "e_high_bonus", "d_metropolis"} ;

        for (String in: inputs) {
            World world = new World();
            world.parse(in+ ".in");
            world.simulate();
            world.print(in+".out");
        }
    }
}
