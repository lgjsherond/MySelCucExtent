Feature: JacPLUS Test

  Scenario Outline: Open JacPLUS Dashboard
    Given Launch JacPLUS home page
    And modify the cookie "<cook>"
#    And reload the page
    And enter credentials
    Then verify the dashboard
    And waiting

    Examples:
      | cook         |
      | syd-as-x1    |
      | syd-as-x2    |
      | syd-as-x3    |
      | syd-as-x4    |
      | syd-as-x5    |
      | syd-as-x6    |
      | syd-as-x7    |
      | syd-as-x7-2  |
      | syd-as-x8    |
      | syd-as-x8-2  |
      | syd-as-x9    |
      | syd-as-x9-2  |
      | syd-as-x10   |
      | syd-as-x10-2 |
