import Navbar from "./components/navbar";
import '../hr/hr.css';
import HRStat from "./components/hrstat";
import ItemForm from "./components/itemForm";


function HR() {
    return (
        <div>
            <div>
            <Navbar />
            </div>
            <div>
                <HRStat />
            </div>
            <div>
                <ItemForm/>
            </div>

            </div>
    )
}
export default HR;