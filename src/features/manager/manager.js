import EmployeeList from "./components/employeelist";
import { getEmployees } from "../../store/action/employee";
import { useDispatch } from "react-redux";
import Navbar from "./components/navbar_manager";

function Manager() {
  const dispatch = useDispatch();
  dispatch(getEmployees());
  return (
    <div className="content">
      <Navbar />
      <EmployeeList />
    </div>
  );
}

export default Manager;
