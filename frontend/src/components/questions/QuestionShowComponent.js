import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';
import { Highlight } from 'react-fast-highlight';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

class QuestionShowComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      question: {
        id: this.props.match.params.id,
        title: '',
        code: '',
        variables: '',
        userId: null,
        poolId: 0,
        questionInstances: [],
      },
    };
  }

  componentDidMount() {
    this.getPool();
  }

  getPool() {
    axios
      .get(
        process.env.REACT_APP_API_URL + '/questions/' + this.state.question.id
      )
      .then(result => {
        this.setState({
          isLoading: false,
          question: result.data,
        });
        this.props.enqueueSnackbar('Datos cargados!', { variant: 'success' });
      })
      .catch(error => {
        this.setState({
          isLoading: false,
          question: [],
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <div class="panelcrear">
              <div class="formcrear">
                <Typography variant="h6" gutterBottom>
                  Pregunta
                </Typography>
                <Typography
                  style={{ fontSize: 20, fontFamily: 'Segoe UI' }}
                  variant="h6"
                  gutterBottom
                >
                  {this.state.question.title}
                </Typography>
                <Highlight languages={['js']} className="my-class">
                  {this.state.question.code}
                </Highlight>

                <Typography variant="h6" gutterBottom>
                  Variables
                </Typography>
                <Highlight languages={['js']} className="my-class">
                  {this.state.question.variables}
                </Highlight>

                <br />

                <div className="btn-group">
                  <Button color="default" component={Link} to="/questions/">
                    Volver
                  </Button>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(QuestionShowComponent);
