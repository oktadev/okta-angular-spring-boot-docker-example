import { browser, by, element } from 'protractor';
import { LoginPage } from './login.po';

describe('Notes', () => {
  let loginPage: LoginPage;

  beforeAll(async () => {
    loginPage = new LoginPage();
    await loginPage.login();
  });

  afterAll(async () => {
    await loginPage.logout();
  });

  beforeEach(async () => {
    await browser.get('/notes');
  });

  it('should have an input and search button', () => {
    expect(element(by.css('app-root app-note form input')).isPresent()).toEqual(true);
    expect(element(by.css('app-root app-note form button')).isPresent()).toEqual(true);
  });
});
