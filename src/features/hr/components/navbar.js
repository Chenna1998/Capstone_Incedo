import { Link, useNavigate } from 'react-router-dom';
import '../../hr/components/navbar.css';
import logoImage from '../../../icons/incedo_logo.png'; 

function Navbar() {

  const navigate = useNavigate();

  const logout = ()=>{
    localStorage.clear();
    navigate('/?msg=looged_out')
  }

  return (
    <nav className="nav">
      <img src={logoImage} alt="Logo" className="logo" />
      <ul className="nav__menu">
        <li className="nav__item">
          <Link to="/hr" className="nav__link">
            HR Dashboard
          </Link>
        </li>
        <li className="nav__item">
          <Link to="/manageronboarding" className="nav__link">
            ManagerOnboarding
          </Link>
        </li>
        <li className="nav__item">
          <Link to="/employeeonboarding" className="nav__link">
            EmployeeOnboarding
          </Link>
        </li>
      </ul>
      <span>Welcome {localStorage.getItem('username')} &nbsp;&nbsp;&nbsp;</span>
      <form className="d-flex search-form" role="search">
        <input className="form-control me-2" placeholder="Search" />
        <button className="btn btn-outline-success" type="submit">Search</button>
      </form>
      &nbsp;&nbsp;
      <button className="btn btn-secondary"  onClick={logout}>LogOut</button>
    </nav>
  );
}

export default Navbar;
