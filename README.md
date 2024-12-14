# Parallel Selenium Test Suite

This repository contains an automated test suite for an e-commerce platform, developed 
using **Selenium** and **TestNG**. It validates a complete user flow including login, product 
inspection, cart functionality, checkout, payment, and logout.

The tests are executed in parallel on multiple browsers (Edge and Firefox) locally and 
on **Sauce Labs** for cross-browser testing. The results are integrated 
with **TestRail** for test management, and screenshots are captured for failing test cases.

## Features

- **End-to-End Test Coverage**: Test the entire flow from login, inspecting products, adding to cart, proceeding through checkout, completing payment, and logging out.
- **Parallel Execution**: Tests can be executed on multiple browsers (Edge and Firefox) locally or on Sauce Labs for cross-browser testing.
- **Thread Safety**: Implements **ThreadLocal** to ensure thread safety during parallel execution.
- **Test Rail Integration**: Results are automatically sent to **TestRail** using **Rest Assured** API.
- **Automatic Screenshot Capture**: Screenshots are taken for failing test cases for easy debugging.
- **Tools Used**:
  - **Selenium** for browser automation.
  - **TestNG** for running tests, managing test cases, and parallel execution.
  - **Rest Assured** for API integration with **TestRail**.
  - **Java** (Reflection, Streams, Interfaces, Annotations) for logic and test execution.

## Project Structure

- **`pages/`**: Contains page object classes for different sections of the application (e.g., `HomePage`, `CartPage`, `CheckoutPage`, etc.).
- **`tests/`**: Contains test classes implementing the test cases.
- **`util/`**: Utility classes (e.g., `MyUtil` for helper methods).
- **`testRailUtility/`**: Classes for interacting with TestRail via REST API (`TestRailListener`, `ListenerCase`).

