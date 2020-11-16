Feature: JacPLUS Test

  Scenario: Open JacPLUS Dashboard
    Given Launch JacPLUS home page
    And modify the cookie
#    And reload the page
    And enter credentials
    Then verify the dashboard
