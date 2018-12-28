import * as React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { FormGroup, Input, Label } from 'reactstrap';
import { Row, Col, Table } from 'reactstrap';
import { IUserCreateDto } from '../../../model/UserCreateDto.model';
import { IState } from '../../../reducers/index';
import { connect } from 'react-redux';
import * as managerActions from '../../../actions/manager/manager.actions';

/*
* The New Cohort Create
*/
export interface IProps {
  modal: boolean
  toggle: (name: string) => void
  modalOff: () => void
  managerPostCohort: (cohortName: string, cohortDescription: string, userList: IUserCreateDto[]) => void
}

interface IComponentState {
  cohortName: any
  cohortDescription: any
  fileReader: any
  fileContent: any
  displayTable: boolean
}

export class CreateNewModalComponent extends React.Component<IProps, IComponentState> {

  constructor(props) {
    super(props);

    this.state = {
      cohortDescription: '',
      cohortName: '',
      displayTable: false,
      fileContent: [],
      fileReader: {
      },
    }
  }

  public handleFileRead = (e) => {
    const content = this.state.fileReader.result;
    const newContent = content.split("\n");
    const userArray = [];

    newContent.forEach((element) => {

      const newElement = element.split(',');

      const userObj = {
        email: newElement[2],
        firstname: newElement[0],
        lastName: newElement[1],
      }
      userArray.push(userObj);

    });

    this.setState({
      ...this.state,
      displayTable: true,
      fileContent: userArray,
    })
  }

  public handleSubmit = (e: any) => {
    const listOfUsers = this.state.fileContent.map((user) => {
      return user as IUserCreateDto
    });

    this.props.managerPostCohort(this.state.cohortName, this.state.cohortDescription, listOfUsers);
  }

  public handlecohortName = (e: any) => {
    this.setState({
      cohortName: e.target.value
    });
  }
  public handleCohortDescription = (e: any) => {
    this.setState({
      cohortDescription: e.target.value
    });
  }

  public handleChange = (selectorFiles: FileList) => {
    const fileReader1 = new FileReader();
    fileReader1.onload = this.handleFileRead;

    this.setState({
      ...this.state,
      fileReader: fileReader1
    });

    fileReader1.readAsText(selectorFiles[0]);
  }

  public tableDrawer = () => {
    const mapContent = this.state.fileContent.map((user, index) => {
      return (
        <tr key={index}>
          <td>{user.firstname}</td>
          <td>{user.lastName}</td>
          <td>{user.email}</td>
        </tr>
      )
    })

    return mapContent;
  }

  public render() {
    const renderTable = this.tableDrawer();

    return (
      <>
        <div>
          <Modal isOpen={this.props.modal} className="manager-comments">
            <ModalHeader>Create New Cohort</ModalHeader>
            <ModalBody className="">
              <Row form>
                <Col xs={12}>
                  <FormGroup>
                    <Label for="cohort-name">Cohort Name</Label>
                    <Input type="text" name="cohort-name" id="cohort-name" placeholder="Name"
                      value={this.state.cohortName}
                      onChange={this.handlecohortName.bind(this)} />
                  </FormGroup>
                </Col>
                <Col xs={12}>
                  <FormGroup>
                    <Label for="cohort-description">Cohort Description</Label>
                    <Input type="textarea" name="cohort-description" id="cohort-description"
                      value={this.state.cohortDescription}
                      onChange={this.handleCohortDescription.bind(this)}
                    />
                  </FormGroup>
                </Col>
              </Row>
              <div className="upload-btn-wrapper">
                <button className="btn-cohort"> Upload File</button>
                <input type="file" onChange={(e) => this.handleChange(e.target.files)} />
                <div>
                  <small> .csv files </small>
                </div>
              </div>
              <div className="mt-3">
                <Table bordered className="table table-sm">
                  <thead className="checkin-table-header">
                    <tr>
                      {/* <th>#</th> */}
                      <th>First Name</th>
                      <th>Last Name</th>
                      <th>Email</th>
                    </tr>
                  </thead>

                  {this.state.displayTable &&
                    <tbody>
                      {renderTable}
                    </tbody>
                  }
                </Table>
              </div>
            </ModalBody>
            <ModalFooter className="flex-btw">
              <Button className="rev-btn " onClick={this.handleSubmit}>Submit</Button>
              <Button className="" color="secondary" onClick={this.props.modalOff}>Cancel</Button>
            </ModalFooter>
          </Modal>
        </div>
      </>
    );
  }
}

const mapStateToProps = (state: IState) => (state.manager)
const mapDispatchToProps = {
  ...managerActions
}
export default connect(mapStateToProps, mapDispatchToProps)(CreateNewModalComponent);