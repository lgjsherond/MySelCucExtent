Feature: JacPLUS Test

  Scenario Outline: Open JacPLUS Dashboard
    Given Launch JacPLUS home page
    And modify the cookie "<cook>"
#    And reload the page
    And enter credentials
    Then verify the dashboard
#    And waiting
    Then Click on AssessON "<cook>"
    Then click back to Bookshelf
    Then  click on eBook title "<cook>"

    Examples:
      | cook         |
      | syd-as-x1    |
