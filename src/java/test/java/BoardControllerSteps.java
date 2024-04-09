package java.test.java;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class BoardControllerSteps {
    private BoardController boardController;
    private boolean moveResult;

    @Given("a board is initialized")
    public void a_board_is_initialized() {
        boardController = new BoardController();
        boardController.initializeBoard();
    }

    @When("a valid move is made")
    public void a_valid_move_is_made() {
        // Assuming Move takes two parameters: the start position and the end position
        Move validMove = new Move("e2", "e4");
        moveResult = boardController.makeMove(validMove);
    }

    @Then("the move should be successful")
    public void the_move_should_be_successful() {
        assertTrue(moveResult);
    }

    @When("an invalid move is made")
    public void an_invalid_move_is_made() {
        // Assuming Move takes two parameters: the start position and the end position
        Move invalidMove = new Move("e7", "e5");
        moveResult = boardController.makeMove(invalidMove);
    }

    @Then("the move should be unsuccessful")
    public void the_move_should_be_unsuccessful() {
        assertFalse(moveResult);
    }
}